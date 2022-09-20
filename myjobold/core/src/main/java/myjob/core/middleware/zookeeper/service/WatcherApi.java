//package myjob.core.middleware.zookeeper.service;
//
//import lombok.extern.slf4j.Slf4j;
//import myjob.core.middleware.zookeeper.util.ZkApi;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
///**
// * 实现Watcher监听
// */
//@Slf4j
//@Component
//public class WatcherApi implements Watcher {
//    @Autowired
//    ZkApi zkApi;
//    @Override
//    public void process(WatchedEvent event) {
//        log.info("【Watcher监听事件】={}",event.getState());
//        log.info("【监听路径为】={}",event.getPath());
//        log.info("【监听的类型为】={}",event.getType()); //  三种监听类型： 创建，删除，更新
//        // TODO Auto-generated method stub
//        if (event.getState() == Event.KeeperState.SyncConnected) {
//            System.out.println("watcher received event");
////            countDownLatch.countDown();
//        }
//        System.out.println("回调watcher1实例： 路径" + event.getPath() + " 类型："+ event.getType());
//        // 事件类型，状态，和检测的路径
//        Event.EventType eventType = event.getType();
//        Event.KeeperState state = event.getState();
//        String watchPath = event.getPath();
//        switch (eventType) {
//            case NodeCreated:
//                break;
//            case NodeDataChanged:
//                break;
//            case NodeChildrenChanged:
//                try {
//                    //处理收到的消息
//                    handleMessage(watchPath);
//                } catch (UnsupportedEncodingException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (KeeperException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                break;
//            default:
//                break;
//        }
//    }
//    @Autowired
//    private ZooKeeper zkClient;
//    public void handleMessage(String watchPath) throws KeeperException,InterruptedException, UnsupportedEncodingException {
//        System.out.println("收到消息");
//        //再监听该子节点
//        List<String> Children = this.getChildren(watchPath);
//        for (String a : Children) {
//            String childrenpath = watchPath + "/" + a;
//            byte[] recivedata = zkApi.getData(childrenpath);
//            String recString = new String(recivedata, "UTF-8");
//            System.out.println("receive the path:" + childrenpath + ":data:"+ recString);
//            //做完了之后，删除该节点
//            zkApi.deletNode(childrenpath, -1);
//        }
//    }
//
//    public List<String> getChildren(String path) throws KeeperException,InterruptedException {
//        //监听该节点子节点的变化情况
//        return zkApi.zooKeeper.getChildren(path, this);
//    }
//    public Stat setData(String path, byte[] data, int version)throws KeeperException, InterruptedException {
//        return this.zooKeeper.setData(path, data, version);
//    }
//}
//
