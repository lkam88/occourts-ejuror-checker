package com.lucaskam.occourts.ejurorChecker;

public enum JusticeCenter {
    NORTH_JUSTICE_CENTER("North Justice Center", "njc", "https://ocscefm1.occourts.org/directory/jury-services/serving-as-juror/njc-callin-information.cfm"),
    CENTRAL_JUSTICE_CENTER("Central Justice Center","cjc", "https://ocscefm1.occourts.org/directory/jury-services/serving-as-juror/cjc-callin-information.cfm"),
    HARBOR_JUSTICE_CENTER("Harbor Justice Center","hjc", "https://ocscefm1.occourts.org/directory/jury-services/serving-as-juror/hjc-callin-information.cfm"),
    WEST_JUSTICE_CENTER("West Justice Center","njc", "https://ocscefm1.occourts.org/directory/jury-services/serving-as-juror/wjc-callin-information.cfm");

    private final String code;
    private final String url;
    private final String name;

    JusticeCenter(String name, String code, String url) {
        this.code = code;
        this.url = url;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }
    

    public String getName() {
        return name;
    }
    
    public static JusticeCenter parseCode(String code) {
        for (JusticeCenter justiceCenter: JusticeCenter.values()) {
            if (justiceCenter.getCode().equalsIgnoreCase(code)) {
                return justiceCenter;
            }
        }
        throw new IllegalArgumentException(String.format("Unexpected justice center code: %s", code));
    }
}

