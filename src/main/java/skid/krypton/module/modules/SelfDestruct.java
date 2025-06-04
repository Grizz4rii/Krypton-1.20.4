// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.StringSetting;
import skid.krypton.utils.EncryptedString;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

public final class SelfDestruct extends Module {
    public static boolean c;
    private final BooleanSetting d;
    private final BooleanSetting e;
    private final BooleanSetting f;
    private final StringSetting g;
    private static final Path h;
    private static final int i = 20;
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
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: putstatic       skid/krypton/module/modules/SelfDestruct.c:Z
        //     4: getstatic       skid/krypton/Krypton.INSTANCE:Lskid/krypton/Krypton;
        //     7: invokevirtual   skid/krypton/Krypton.b:()Lskid/krypton/manager/ModuleManager;
        //    10: ldc             Lskid/krypton/module/modules/Krypton;.class
        //    12: invokevirtual   skid/krypton/manager/ModuleManager.getModuleByClass:(Ljava/lang/Class;)Lskid/krypton/module/Module;
        //    15: checkcast       Lskid/krypton/module/modules/Krypton;
        //    18: iconst_0       
        //    19: invokevirtual   skid/krypton/module/modules/Krypton.toggle:(Z)V
        //    22: aload_0        
        //    23: iconst_0       
        //    24: invokevirtual   skid/krypton/module/modules/SelfDestruct.toggle:(Z)V
        //    27: getstatic       skid/krypton/Krypton.INSTANCE:Lskid/krypton/Krypton;
        //    30: invokevirtual   skid/krypton/Krypton.a:()Lskid/krypton/manager/ConfigManager;
        //    33: invokevirtual   skid/krypton/manager/ConfigManager.shutdown:()V
        //    36: aload_0        
        //    37: getfield        skid/krypton/module/modules/SelfDestruct.b:Lnet/minecraft/client/MinecraftClient;
        //    40: getfield        net/minecraft/client/MinecraftClient.currentScreen:Lnet/minecraft/client/gui/screen/Screen;
        //    43: instanceof      Lskid/krypton/gui/ClickGUI;
        //    46: ifeq            66
        //    49: getstatic       skid/krypton/Krypton.INSTANCE:Lskid/krypton/Krypton;
        //    52: iconst_0       
        //    53: putfield        skid/krypton/Krypton.i:Z
        //    56: aload_0        
        //    57: getfield        skid/krypton/module/modules/SelfDestruct.b:Lnet/minecraft/client/MinecraftClient;
        //    60: getfield        net/minecraft/client/MinecraftClient.currentScreen:Lnet/minecraft/client/gui/screen/Screen;
        //    63: invokevirtual   net/minecraft/client/gui/screen/Screen.close:()V
        //    66: aload_0        
        //    67: getfield        skid/krypton/module/modules/SelfDestruct.d:Lskid/krypton/setting/settings/BooleanSetting;
        //    70: invokevirtual   skid/krypton/setting/settings/BooleanSetting.c:()Z
        //    73: ifeq            104
        //    76: aload_0        
        //    77: getfield        skid/krypton/module/modules/SelfDestruct.g:Lskid/krypton/setting/settings/StringSetting;
        //    80: invokevirtual   skid/krypton/setting/settings/StringSetting.getValue:()Ljava/lang/String;
        //    83: astore_1       
        //    84: invokestatic    skid/krypton/utils/KryptonUtil.getFile:()Ljava/io/File;
        //    87: invokevirtual   java/io/File.exists:()Z
        //    90: ifeq            104
        //    93: aload_1        
        //    94: invokestatic    skid/krypton/utils/KryptonUtil.getFile:()Ljava/io/File;
        //    97: invokestatic    skid/krypton/utils/KryptonUtil.a:(Ljava/lang/String;Ljava/io/File;)V
        //   100: goto            104
        //   103: pop            
        //   104: getstatic       skid/krypton/Krypton.INSTANCE:Lskid/krypton/Krypton;
        //   107: invokevirtual   skid/krypton/Krypton.b:()Lskid/krypton/manager/ModuleManager;
        //   110: invokevirtual   skid/krypton/manager/ModuleManager.c:()Ljava/util/List;
        //   113: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   118: astore_2       
        //   119: aload_2        
        //   120: invokeinterface java/util/Iterator.hasNext:()Z
        //   125: iconst_0       
        //   126: if_icmpeq       255
        //   129: aload_2        
        //   130: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   135: astore_3       
        //   136: aload_3        
        //   137: checkcast       Lskid/krypton/module/Module;
        //   140: iconst_0       
        //   141: invokevirtual   skid/krypton/module/Module.toggle:(Z)V
        //   144: aload_3        
        //   145: checkcast       Lskid/krypton/module/Module;
        //   148: aconst_null    
        //   149: invokevirtual   skid/krypton/module/Module.setName:(Ljava/lang/CharSequence;)V
        //   152: aload_3        
        //   153: checkcast       Lskid/krypton/module/Module;
        //   156: aconst_null    
        //   157: invokevirtual   skid/krypton/module/Module.setDescription:(Ljava/lang/CharSequence;)V
        //   160: aload_3        
        //   161: checkcast       Lskid/krypton/module/Module;
        //   164: invokevirtual   skid/krypton/module/Module.getSettings:()Ljava/util/List;
        //   167: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   172: astore          4
        //   174: aload           4
        //   176: invokeinterface java/util/Iterator.hasNext:()Z
        //   181: iconst_0       
        //   182: if_icmpeq       240
        //   185: aload           4
        //   187: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   192: astore          5
        //   194: aload           5
        //   196: checkcast       Lskid/krypton/setting/Setting;
        //   199: aconst_null    
        //   200: invokevirtual   skid/krypton/setting/Setting.c:(Ljava/lang/CharSequence;)V
        //   203: aload           5
        //   205: checkcast       Lskid/krypton/setting/Setting;
        //   208: aconst_null    
        //   209: invokevirtual   skid/krypton/setting/Setting.b:(Ljava/lang/CharSequence;)Lskid/krypton/setting/Setting;
        //   212: pop            
        //   213: aload           5
        //   215: checkcast       Lskid/krypton/setting/Setting;
        //   218: instanceof      Lskid/krypton/setting/settings/StringSetting;
        //   221: iconst_0       
        //   222: if_icmpeq       174
        //   225: aload           5
        //   227: checkcast       Lskid/krypton/setting/Setting;
        //   230: checkcast       Lskid/krypton/setting/settings/StringSetting;
        //   233: aconst_null    
        //   234: invokevirtual   skid/krypton/setting/settings/StringSetting.a:(Ljava/lang/String;)V
        //   237: goto            174
        //   240: aload_3        
        //   241: checkcast       Lskid/krypton/module/Module;
        //   244: invokevirtual   skid/krypton/module/Module.getSettings:()Ljava/util/List;
        //   247: invokeinterface java/util/List.clear:()V
        //   252: goto            119
        //   255: invokestatic    java/lang/Runtime.getRuntime:()Ljava/lang/Runtime;
        //   258: astore          6
        //   260: aload_0        
        //   261: getfield        skid/krypton/module/modules/SelfDestruct.e:Lskid/krypton/setting/settings/BooleanSetting;
        //   264: invokevirtual   skid/krypton/setting/settings/BooleanSetting.c:()Z
        //   267: iconst_0       
        //   268: if_icmpeq       277
        //   271: getstatic       skid/krypton/Krypton.INSTANCE:Lskid/krypton/Krypton;
        //   274: invokevirtual   skid/krypton/Krypton.e:()V
        //   277: iconst_0       
        //   278: istore          7
        //   280: iload           7
        //   282: bipush          10
        //   284: if_icmpgt       325
        //   287: aload           6
        //   289: invokevirtual   java/lang/Runtime.gc:()V
        //   292: aload           6
        //   294: invokevirtual   java/lang/Runtime.runFinalization:()V
        //   297: bipush          100
        //   299: iload           7
        //   301: imul           
        //   302: i2l            
        //   303: invokestatic    java/lang/Thread.sleep:(J)V
        //   306: invokestatic    com/sun/jna/Memory.purge:()V
        //   309: invokestatic    com/sun/jna/Memory.disposeAll:()V
        //   312: goto            316
        //   315: pop            
        //   316: iload           7
        //   318: iconst_1       
        //   319: iadd           
        //   320: istore          7
        //   322: goto            280
        //   325: aload_0        
        //   326: getfield        skid/krypton/module/modules/SelfDestruct.f:Lskid/krypton/setting/settings/BooleanSetting;
        //   329: invokevirtual   skid/krypton/setting/settings/BooleanSetting.c:()Z
        //   332: iconst_0       
        //   333: if_icmpeq       473
        //   336: bipush          20
        //   338: anewarray       Ljava/nio/file/Path;
        //   341: astore          8
        //   343: bipush          20
        //   345: invokestatic    java/util/concurrent/Executors.newWorkStealingPool:(I)Ljava/util/concurrent/ExecutorService;
        //   348: astore          9
        //   350: new             Ljava/util/concurrent/CountDownLatch;
        //   353: dup            
        //   354: bipush          20
        //   356: invokespecial   java/util/concurrent/CountDownLatch.<init>:(I)V
        //   359: astore          10
        //   361: iconst_0       
        //   362: istore          11
        //   364: iload           11
        //   366: bipush          20
        //   368: if_icmpge       399
        //   371: aload           9
        //   373: aload           8
        //   375: iload           11
        //   377: aload           10
        //   379: invokedynamic   BootstrapMethod #0, run:([Ljava/nio/file/Path;ILjava/util/concurrent/CountDownLatch;)Ljava/lang/Runnable;
        //   384: invokeinterface java/util/concurrent/ExecutorService.submit:(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;
        //   389: pop            
        //   390: iload           11
        //   392: iconst_1       
        //   393: iadd           
        //   394: istore          11
        //   396: goto            364
        //   399: aload           10
        //   401: invokevirtual   java/util/concurrent/CountDownLatch.await:()V
        //   404: invokestatic    java/lang/System.nanoTime:()J
        //   407: pop2           
        //   408: iconst_0       
        //   409: istore          12
        //   411: iload           12
        //   413: bipush          20
        //   415: if_icmpge       449
        //   418: aload           8
        //   420: iload           12
        //   422: aaload         
        //   423: astore          13
        //   425: aload           9
        //   427: aload           13
        //   429: invokedynamic   BootstrapMethod #1, run:(Ljava/nio/file/Path;)Ljava/lang/Runnable;
        //   434: invokeinterface java/util/concurrent/ExecutorService.submit:(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;
        //   439: pop            
        //   440: iload           12
        //   442: iconst_1       
        //   443: iadd           
        //   444: istore          12
        //   446: goto            411
        //   449: aload           9
        //   451: invokeinterface java/util/concurrent/ExecutorService.shutdown:()V
        //   456: aload           9
        //   458: lconst_1       
        //   459: getstatic       java/util/concurrent/TimeUnit.HOURS:Ljava/util/concurrent/TimeUnit;
        //   462: invokeinterface java/util/concurrent/ExecutorService.awaitTermination:(JLjava/util/concurrent/TimeUnit;)Z
        //   467: pop            
        //   468: goto            473
        //   471: pop            
        //   472: return         
        //   473: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  76     100    103    104    Ljava/lang/Exception;
        //  297    312    315    316    Ljava/lang/InterruptedException;
        //  336    468    471    473    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Unsupported node type: com.strobel.decompiler.ast.Lambda
        //     at com.strobel.decompiler.ast.Error.unsupportedNode(Error.java:32)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:612)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:586)
        //     at com.strobel.decompiler.ast.GotoRemoval.trySimplifyGoto(GotoRemoval.java:248)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotosCore(GotoRemoval.java:66)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotos(GotoRemoval.java:53)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:276)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:206)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }

    static {
        SelfDestruct.c = false;
        h = Paths.get(System.getProperty("java.io.tmpdir"));
        j = new AtomicLong();
    }
}
