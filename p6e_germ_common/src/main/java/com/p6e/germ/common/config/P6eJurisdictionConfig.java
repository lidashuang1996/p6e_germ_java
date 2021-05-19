package com.p6e.germ.common.config;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionConfig {

    private Voucher voucher;

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public static class Voucher {
        private String[] ip = new String[0];
        private String[] token = new String[0];

        public String[] getIp() {
            return ip;
        }

        public void setIp(String[] ip) {
            this.ip = ip;
        }

        public String[] getToken() {
            return token;
        }

        public void setToken(String[] token) {
            this.token = token;
        }
    }
}
