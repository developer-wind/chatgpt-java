package com.wind;

import java.util.ArrayList;
import java.util.Arrays;

public class Authentication {
    ArrayList<String> pks;

    public Authentication(String[] pkList) {
        pks = new ArrayList<>();
        pks.addAll(Arrays.asList(pkList));
    }

    /**
     * getKey 返回私钥
     * @param userId 同一用户服务运行期间始终使用同一私钥
     * @return
     */
    public String getKey(String userId) {
        synchronized (this) {
            return pks.get(getIndex(userId));
        }
    }

    private int getIndex(String userId) {
        int i = 0;
        for (byte aByte : userId.getBytes()) {
            i += aByte;
        }
        return Math.abs(i % pks.size());
    }
}
