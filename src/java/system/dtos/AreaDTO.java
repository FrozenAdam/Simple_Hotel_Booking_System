/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.dtos;

/**
 *
 * @author theFrozenAdam
 */
public class AreaDTO {
    private int areaID;
    private String areaName;

    public AreaDTO(int areaID, String areaName) {
        this.areaID = areaID;
        this.areaName = areaName;
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
