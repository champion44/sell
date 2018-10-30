package com.imooc.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class WxPictures {
	@Id
    private String id;

    private String type;

    private String picturelink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPicturelink() {
        return picturelink;
    }

    public void setPicturelink(String picturelink) {
        this.picturelink = picturelink == null ? null : picturelink.trim();
    }
}