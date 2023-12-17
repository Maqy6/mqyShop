package com.shop.pojo;

    public class Admin {
        private Integer aId;    //用户id

        private String aName;   //用户账号

        private String aPass;   //用户密码

        private Integer aType;  //用户类型：管理员或普通用户

        public Integer getaId() {
            return aId;
        }

        public void setaId(Integer aId) {
            this.aId = aId;
        }

        public String getaName() {
            return aName;
        }

        public void setaName(String aName) {
            this.aName = aName == null ? null : aName.trim();
        }

        public String getaPass() {
            return aPass;
        }

        public void setaPass(String aPass) {
            this.aPass = aPass == null ? null : aPass.trim();
        }

        public Integer getaType() {
            return aType;
        }

        public void setaType(Integer aType) {
            this.aType = aType;
        }
    }