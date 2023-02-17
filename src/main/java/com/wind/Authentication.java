package com.wind;

import java.util.NoSuchElementException;

public class Authentication {
    String[] pks;

    public Authentication(String[] pkList) {
        if (pkList.length == 0) {
            throw new NoSuchElementException();
        }
        pks = pkList;
    }

    /**
     * getKey 返回私钥
     * @param user 同一用户服务运行期间始终使用同一私钥
     * @return
     */
    public String getKey(String user) {
        synchronized (this) {
            int i = 0;
            for (byte aByte : user.getBytes()) {
                i += aByte;
            }
            int index = i % pks.length;
            return pks[Math.abs(index)];
        }
    }
}
