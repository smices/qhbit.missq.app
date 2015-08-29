package com.imissq.model;

public class UpdateBean extends BaseBean {
    private String code;
    private Download msg;


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public Download getMsg() {
        return msg;
    }


    public void setMsg(Download msg) {
        this.msg = msg;
    }


    public static class Download {
        String Platform;
        String LastVersion;
        int LastVersionCode;
        String ReleaseTime;
        //String LowCompatible;
        String Description;
        String Download;

        public String getPlatform() {
            return Platform;
        }

        public void setPlatform(String platform) {
            Platform = platform;
        }

        public String getLastVersion() {
            return LastVersion;
        }

        public void setLastVersion(String lastVersion) {
            LastVersion = lastVersion;
        }

        public int getLastVersionCode() {
            return LastVersionCode;
        }

        public void setLastVersionCode(int lastVersionCode) {
            LastVersionCode = lastVersionCode;
        }

        public String getReleaseTime() {
            return ReleaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            ReleaseTime = releaseTime;
        }

        /*public String getLowCompatible() {
            return LowCompatible;
        }
        public void setLowCompatible(String lowCompatible) {
            LowCompatible = lowCompatible;
        }*/
        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getDownload() {
            return Download;
        }

        public void setDownload(String download) {
            Download = download;
        }


    }

}
