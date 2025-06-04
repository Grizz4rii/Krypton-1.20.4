package skid.krypton.module.modules;

import com.sun.jna.Memory;
import skid.krypton.gui.ClickGUI;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.Setting;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.StringSetting;
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
    public static boolean c;
    private final BooleanSetting d;
    private final BooleanSetting e;
    private final BooleanSetting f;
    private final StringSetting g;
    private static final Path h;
    private static final AtomicLong j;

    public SelfDestruct() {
        super(EncryptedString.a("Self Destruct"), EncryptedString.a("Removes the client from your game |Credits to Argon for deletion|"), -1, Category.e);
        this.d = new BooleanSetting(EncryptedString.a("Replace Mod"), true).a(EncryptedString.a("Repalces the mod with the original JAR file of the ImmediatelyFast mod"));
        this.e = new BooleanSetting(EncryptedString.a("Save Last Modified"), true).a(EncryptedString.a("Saves the last modified date after self destruct"));
        this.f = new BooleanSetting(EncryptedString.a("USN Journal Cleaner"), true);
        this.g = new StringSetting(EncryptedString.a("Replace URL"), "https://cdn.modrinth.com/data/8shC1gFX/versions/sXO3idkS/BetterF3-11.0.1-Fabric-1.21.jar");
        this.a(this.d, this.e, this.f, this.g);
    }

    @Override
    public void onEnable() {
        c = true;
        skid.krypton.Krypton.INSTANCE.b().getModuleByClass(Krypton.class).toggle(false);
        this.toggle(false);
        skid.krypton.Krypton.INSTANCE.a().shutdown();
        if (this.b.currentScreen instanceof ClickGUI) {
            skid.krypton.Krypton.INSTANCE.shouldPreventClose = false;
            this.b.currentScreen.close();
        }
        if (this.d.c()) {
            try {
                String string = this.g.getValue();
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
                ((Setting)setting).c(null);
                ((Setting)setting).b(null);
                if (!((Setting)setting instanceof StringSetting)) continue;
                ((StringSetting)((Setting)setting)).a(null);
            }
            ((Module)module).getSettings().clear();
        }
        Runtime runtime = Runtime.getRuntime();
        if (this.e.c()) {
            skid.krypton.Krypton.INSTANCE.e();
        }
        for (int i = 0; i <= 10; ++i) {
            runtime.gc();
            runtime.runFinalization();
            try {
                Thread.sleep(100 * i);
                Memory.purge();
                Memory.disposeAll();
                continue;
            }
            catch (InterruptedException interruptedException) {}
        }
        if (this.f.c()) {
            try {
                Path[] pathArray = new Path[20];
                ExecutorService executorService = Executors.newWorkStealingPool(20);
                CountDownLatch countDownLatch = new CountDownLatch(20);
                for (int i = 0; i < 20; ++i) {
                    final int n = i;
                    executorService.submit(() -> {
                        try {
                            pathArray[n] = Files.createTempFile(h, "meta", ".tmp");
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
                        while (j.get() < 500000L) {
                            try {
                                Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
                                boolean bl = !((Boolean) Files.getAttribute(path, "dos:archive"));
                                Files.setAttribute(path, "dos:archive", bl);
                            }
                            catch (IOException iOException) {}
                            j.addAndGet(2L);
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
        SelfDestruct.c = false;
        h = Paths.get(System.getProperty("java.io.tmpdir"));
        j = new AtomicLong();
    }
}
