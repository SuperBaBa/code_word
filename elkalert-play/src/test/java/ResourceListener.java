import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author:tennyson date:2020/6/19
 **/
public class ResourceListener {
    private static ExecutorService fixedThreadPool = Executors
            .newFixedThreadPool(5);
    private WatchService ws;
    private String listenerPath;

    private ResourceListener(String path) {
        try {
            ws = FileSystems.getDefault().newWatchService();
            this.listenerPath = path;
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        fixedThreadPool.execute(new Listner(ws, this.listenerPath));
    }

    public static void addListener(String path) throws IOException {
        ResourceListener resourceListener = new ResourceListener(path);
        Path p = Paths.get(path);
        p.register(resourceListener.ws, StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_CREATE);
    }

    public static void main(String[] args) throws IOException {
        ResourceListener.addListener("E:\\yto\\nacos-docker\\example");
    }
}

class Listner implements Runnable {
    private WatchService service;
    private String rootPath;

    public Listner(WatchService service, String rootPath) {
        this.service = service;
        this.rootPath = rootPath;
    }

    public void run() {
        try {
            while (true) {
                WatchKey watchKey = service.take();
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for (WatchEvent<?> event : watchEvents) {
                    String path = event.context().toString();
                    if (path.endsWith("config.properties")) {
                        System.out.println("[" + rootPath + "/" + event.context() + "]文件发生了[" + event.kind() + "]事件");
                        PropertyUtil.init();//配置变更初始化
                    }
                }
                watchKey.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                service.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
