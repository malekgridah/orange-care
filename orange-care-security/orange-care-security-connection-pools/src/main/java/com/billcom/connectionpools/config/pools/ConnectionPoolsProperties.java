package com.billcom.connectionpools.config.pools;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bscs.connection")
public class ConnectionPoolsProperties {

    private PoolsProperties pools = new PoolsProperties();


    public PoolsProperties getPools() {
        return pools;
    }

    public void setPools(PoolsProperties pools) {
        this.pools = pools;
    }

    public static class PoolsProperties {

        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
