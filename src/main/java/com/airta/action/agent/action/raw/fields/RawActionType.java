package com.airta.action.agent.action.raw.fields;

import com.google.gson.annotations.SerializedName;

public enum RawActionType {

    @SerializedName("CLICK")
    CLICK,
    @SerializedName("SUBMIT")
    SUBMIT,
    @SerializedName("INPUT")
    INPUT,
    @SerializedName("SELECT")
    SELECT,
    @SerializedName("SCROLL")
    SCROLL,
    @SerializedName("NAVI_BACK")
    NAVI_BACK,
    @SerializedName("NAVI_FORWARD")
    NAVI_FORWARD,
    @SerializedName("NAVI_REFRESH")
    NAVI_REFRESH,
    @SerializedName("SETVALUE")
    SETVALUE,
    @SerializedName("JSLOGCAPTURE")
    JSLOGCAPTURE,
    @SerializedName("SCREENSHOT")
    SCREENSHOT,
    @SerializedName("PRINTSOURCE")
    PRINTSOURCE,
    @SerializedName("PRINTCHILDRENLINK")
    PRINTCHILDRENLINK,
    @SerializedName("REPORT")
    REPORT,
    @SerializedName("UPDATEMAP")
    UPDATEMAP,
    @SerializedName("REPORTLOG")
    REPORTLOG,
    @SerializedName("INITHOME")
    INITHOME,
    @SerializedName("TEARDOWN")
    TEARDOWN,
    @SerializedName("CLEAR")
    CLEAR,
    @SerializedName("STARTFROM")
    STARTFROM,
    @SerializedName("GOTOPAGE")
    GOTOPAGE,
    @SerializedName("RESTART")
    RESTART,
    @SerializedName("RESTARTTOPAGE")
    RESTARTTOPAGE
}
