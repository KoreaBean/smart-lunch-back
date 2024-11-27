package hello.lunchback.waitManagement;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WaitingManager {

    private  static Map<Integer, Deque<Integer>> waiting = new HashMap<>();



    public void add(Integer storeId,Integer orderId){
        waiting.computeIfAbsent(storeId, k -> new ArrayDeque<>());
        waiting.get(storeId).add(orderId);
    }

    public Integer get(Integer storeId){
        Deque<Integer> queue = waiting.get(storeId);
        if (queue == null || queue.isEmpty()){
            return 0;
        }
        return queue.poll();
    }

    public Integer busy(Integer storeId){
        Integer size = 0;
        //size = waiting.get(storeId).size();
        Deque<Integer> deque = waiting.get(storeId);
        if (deque == null){
            return 0;
        }
        if (deque.isEmpty()){
            return 0;
        }
        return deque.size();
    }

    public Integer findUser(Integer storeId, Integer orderId){
        Integer wait = 0;
        Deque<Integer> deque = waiting.get(storeId);
        if (deque == null){
            return  0;
        }
        if (deque.isEmpty()){
            return 0;
        }
        for (Integer list : waiting.get(storeId)) {
            if (list == null){
                return 0;
            }
            if (!list.equals(orderId)){
                wait++;
            }
            if (list.equals(orderId)){
                return wait;
            }
        }
        if (wait == 0){
            return  0;
        }
        return wait;



    }

}
