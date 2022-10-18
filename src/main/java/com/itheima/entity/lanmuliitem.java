package com.itheima.entity;

public class lanmuliitem {
      private Integer id;
      private Integer parentid;
      private String itemname;
      private String itemques;
      private String itemansw;
      private String itempic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemques() {
        return itemques;
    }

    public void setItemques(String itemques) {
        this.itemques = itemques;
    }

    public String getItemansw() {
        return itemansw;
    }

    public void setItemansw(String itemansw) {
        this.itemansw = itemansw;
    }

    public String getItempic() {
        return itempic;
    }

    public void setItempic(String itempic) {
        this.itempic = itempic;
    }
}
