package org.scadalts.e2e.common.impl.logo;

import org.scadalts.e2e.common.core.utils.AsciiHeaders;

public final class AsciiHeadersImpl implements AsciiHeaders {

    public AsciiHeadersImpl() {}

    public static final String MAIN_HEADER_WITH_VERSION = "\n\n" +
                                        "___________________________________________________________________\n" +
                                        "__  ___/___________ ______  /_____ _      ___  ____/_|__ \\__  ____/\n" +
                                        "_____ \\_  ___/  __ `/  __  /_  __ `/________  __/  ____/ /_  __/   \n" +
                                        "____/ // /__ / /_/ // /_/ / / /_/ /_/_____/  /___  _  __/_  /___   \n" +
                                        "/____/ \\___/ \\__,_/ \\__,_/  \\__,_/        /_____/  /____//_____/\n" +
                                        " Scada-LTS-E2E (v{0})";

    public static final String MAIN_HEADER = "\n\n" +
            "___________________________________________________________________\n" +
            "__  ___/___________ ______  /_____ _      ___  ____/_|__ \\__  ____/\n" +
            "_____ \\_  ___/  __ `/  __  /_  __ `/________  __/  ____/ /_  __/   \n" +
            "____/ // /__ / /_/ // /_/ / / /_/ /_/_____/  /___  _  __/_  /___   \n" +
            "/____/ \\___/ \\__,_/ \\__,_/  \\__,_/        /_____/  /____//_____/\n";


    @Override
    public String getMainHeaderWithVersion() {
        return MAIN_HEADER_WITH_VERSION;
    }

    @Override
    public String getMainHeader() {
        return MAIN_HEADER;
    }
}
