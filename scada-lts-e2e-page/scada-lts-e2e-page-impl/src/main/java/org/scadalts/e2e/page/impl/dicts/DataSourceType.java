package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum DataSourceType implements DictionaryObject {

    ONE_WIRE("1-wire","8"),
    ASCII_FILE_READER("ASCII File Reader","33"),
    ASCII_SERIAL("ASCII Serial","34"),
    BAC_NET_I_P("BACnet I/P","10"),
    DNP3_IP("DNP3 IP", "21"),
    DNP3_SERIAL("DNP3 Serial", "22"),
    DR_STORAGE_HT_5B("Dr.Storage HT-5B", "38"),
    GALIL_DMC_21X2("Galil DMC-21x2", "14"),
    HTTP_IMAGE("HTTP Image", "15"),
    HTTP_RECEIVER("HTTP Receiver", "7"),
    HTTP_RETRIEVER("HTTP Retriever", "11"),
    IEC101_ETHERNET("IEC101 Ethernet", "36"),
    IEC101_SERIAL("IEC101 Serial", "35"),
    INTERNAL_DATA_SOURCE("Internal Data Source", "27"),
    JMX("JMX", "26"),
    M_BUS("M Bus","20"),
    META_DATA_SOURCE("Meta Data Source", "9"),
    MITSUBISHI_ALPHA2("Mitsubishi Alpha2", "39"),
    MODBUS_IP("Modbus IP", "3"),
    MODBUS_SERIAL("Modbus Serial", "2"),
    NMEA_LISTENER("NMEA listener", "13"),
    OPC_DA("OPC DA", "32"),
    OPEN_V4J("OpenV4J", "19"),
    PACHUBE("Pachube", "23"),
    POP3_EMAIL("POP3 Email","12"),
    SEROTONIN_PERSISTENT_TCP("Serotonin Persistent TCP", "24"),
    SNMP("SNMP", "5"),
    SQL("SQL", "6"),
    VMSTAT_DATA_SOURCE("VMStat Data Source", "17"),
    VIRTUAL_DATA_SOURCE("Virtual Data Source", "1"),
    NONE("none", "");

    private final String name;
    private final String id;

    DataSourceType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static DataSourceType getType(String typeName) {
        return Stream.of(DataSourceType.values())
                .filter(a -> a.name.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
