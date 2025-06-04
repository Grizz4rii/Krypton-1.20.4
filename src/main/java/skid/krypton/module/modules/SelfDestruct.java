package skid.krypton.module.modules;

import com.sun.jna.Memory;
import skid.krypton.gui.ClickGUI;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.Setting;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.StringSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.KryptonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class SelfDestruct extends Module {
    private static final AtomicLong COUNT;
    private static final Path TEMP;
    public static boolean DESTRUCTED;
    private final BooleanSetting replace;
    private final BooleanSetting lastModified;
    private final BooleanSetting journal;
    private final StringSetting replaceURL;

    public SelfDestruct() {
        super(EncryptedString.of("Self Destruct"), EncryptedString.of("Removes the client from your game |Credits to Argon for deletion|"), -1, Category.CLIENT);
        this.replace = new BooleanSetting(EncryptedString.of("Replace Mod"), true).setDescription(EncryptedString.of("Repalces the mod with the original JAR file of the ImmediatelyFast mod"));
        this.lastModified = new BooleanSetting(EncryptedString.of("Save Last Modified"), true).setDescription(EncryptedString.of("Saves the last modified date after self destruct"));
        this.journal = new BooleanSetting(EncryptedString.of("USN Journal Cleaner"), true);
        this.replaceURL = new StringSetting(EncryptedString.of("Replace URL"), "https://cdn.modrinth.com/data/8shC1gFX/versions/sXO3idkS/BetterF3-11.0.1-Fabric-1.21.jar");
        this.addSettings(this.replace, this.lastModified, this.journal, this.replaceURL);
    }

    @Override
    public void onEnable() {
        DESTRUCTED = true;
        skid.krypton.Krypton.INSTANCE.b().getModuleByClass(Krypton.class).toggle(false);
        this.toggle(false);
        skid.krypton.Krypton.INSTANCE.a().shutdown();
        if (this.mc.currentScreen instanceof ClickGUI) {
            skid.krypton.Krypton.INSTANCE.shouldPreventClose = false;
            this.mc.currentScreen.close();
        }
        if (this.replace.getValue()) {
            try {
                String string = this.replaceURL.getValue();
                if (KryptonUtil.getFile().exists()) {
                    KryptonUtil.a(string, KryptonUtil.getFile());
                }
            }
            catch (Exception exception) {}
        }
        for (Module module : skid.krypton.Krypton.INSTANCE.b().c()) {
            ((Module)module).toggle(false);
            ((Module)module).setName(null);
            ((Module)module).setDescription(null);
            for (Setting setting : ((Module)module).getSettings()) {
                ((Setting)setting).getDescription(null);
                ((Setting)setting).setDescription(null);
                if (!((Setting)setting instanceof StringSetting)) continue;
                ((StringSetting)((Setting)setting)).setValue(null);
            }
            ((Module)module).getSettings().clear();
        }
        Runtime runtime = Runtime.getRuntime();
        if (this.lastModified.getValue()) {
            skid.krypton.Krypton.INSTANCE.e();
        }
        for (int i = 0; i <= 10; ++i) {
            runtime.gc();
            runtime.runFinalization();
            try {
                Thread.sleep(100 * i);
                Memory.purge();
                Memory.disposeAll();
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }
        }
        if (this.journal.getValue()) {
            try {
                Path[] pathArray = new Path[20];
                ExecutorService executorService = Executors.newWorkStealingPool(20);
                CountDownLatch countDownLatch = new CountDownLatch(20);
                for (int i = 0; i < 20; ++i) {
                    final int n = i;
                    executorService.submit(() -> {
                        try {
                            pathArray[n] = Files.createTempFile(TEMP, "meta", ".tmp");
                            countDownLatch.countDown();
                        } catch (Throwable _t) {
                            _t.printStackTrace(System.err);
                        }
                    });
                }
                countDownLatch.await();
                System.nanoTime();
                for (int i = 0; i < 20; ++i) {
                    Path path = pathArray[i];
                    executorService.submit(() -> {
                        while (COUNT.get() < 500000L) {
                            try {
                                Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
                                boolean bl = !((Boolean) Files.getAttribute(path, "dos:archive"));
                                Files.setAttribute(path, "dos:archive", bl);
                            }
                            catch (IOException iOException) {}
                            COUNT.addAndGet(2L);
                        }
                    });
                }
                executorService.shutdown();
                executorService.awaitTermination(1L, TimeUnit.HOURS);
            }
            catch (Exception exception) {
                return;
            }
        }
    }

    static {
        SelfDestruct.DESTRUCTED = false;
        TEMP = Paths.get(System.getProperty("java.io.tmpdir"));
        COUNT = new AtomicLong();
    }
}
