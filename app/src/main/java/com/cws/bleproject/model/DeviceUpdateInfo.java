package com.cws.bleproject.model;

import java.io.Serializable;

public class DeviceUpdateInfo implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public int device_id;

    public int version;

    public String file;

    public String info_ch;

    public String info_en;

    public DeviceUpdateInfo() {

    }

    public DeviceUpdateInfo(int version, int device_id, String info_ch, String info_en) {
        this.version = version;
        this.device_id = device_id;
        this.info_ch = info_ch;
        this.info_en = info_en;
    }

    public DeviceUpdateInfo(int device_id, int version, String file, String info_ch, String info_en) {
        this.device_id = device_id;
        this.version = version;
        this.file = file;
        this.info_ch = info_ch;
        this.info_en = info_en;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getInfo_ch() {
        return info_ch;
    }

    public void setInfo_ch(String info_ch) {
        this.info_ch = info_ch;
    }

    public String getInfo_en() {
        return info_en;
    }

    public void setInfo_en(String info_en) {
        this.info_en = info_en;
    }

    @Override
    public String toString() {
        return "[id = " + device_id
                + ",version = " + version
                + ",file = " + file
                + ",info_ch = " + info_ch
                + ",info_en = " + info_en
                + "]";
    }
}
