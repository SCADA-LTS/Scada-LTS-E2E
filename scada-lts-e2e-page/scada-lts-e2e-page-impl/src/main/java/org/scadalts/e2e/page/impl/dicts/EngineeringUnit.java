package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class EngineeringUnit {

    @Getter
    public enum Acceleration implements DictionaryObject {
        METERS_PER_SECOND_PER_SECOND("meters per second per second");

        private final String name;

        Acceleration(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Area implements DictionaryObject {
        SQUARE_METERS("square meters"),
        SQUARE_CENTIMETERS("square centimeters"),
        SQUARE_FEET("square feet"),
        SQUARE_INCHES("square inches");

        private final String name;

        Area(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Currency implements DictionaryObject {
        CURRENCY_1("currency 1"),
        CURRENCY_2("currency 2"),
        CURRENCY_3("currency 3"),
        CURRENCY_4("currency 4"),
        CURRENCY_5("currency 5"),
        CURRENCY_6("currency 6"),
        CURRENCY_7("currency 7"),
        CURRENCY_8("currency 8"),
        CURRENCY_9("currency 9"),
        CURRENCY_10("currency 10");

        private final String name;

        Currency(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Electrical implements DictionaryObject {
        MILLIAMPERES("milliamperes"),
        AMPERES("amperes"),
        AMPERES_PER_METER("amperes per meter"),
        AMPERES_PER_SQUARE_METER("amperes per square meter"),
        AMPERE_SQUARE_METERS("ampere square meters"),
        FARADS("farads"),
        HENRYS("henrys"),
        OHMS("ohms"),
        OHM_METERS("ohm meters"),
        MILLIOHMS("milliohms"),
        KILOHMS("kilohms"),
        MEGOHMS("megohms"),
        SIEMENS("siemens"),
        SIEMENS_PER_METER("siemens per meter"),
        TESLAS("teslas"),
        VOLTS("volts"),
        MILLIVOLTS("millivolts"),
        KILOVOLTS("kilovolts"),
        MEGAVOLTS("megavolts"),
        VOLT_AMPERES("volt amperes"),
        KILOVOLT_AMPERES("kilovolt amperes"),
        MEGAVOLT_AMPERES("megavolt amperes"),
        VOLT_AMPERES_REACTIVE("volt amperes reactive"),
        KILOVOLT_AMPERES_REACTIVE("kilovolt amperes reactive"),
        MEGAVOLT_AMPERES_REACTIVE("megavolt amperes reactive"),
        VOLTS_PER_DEGREE_KELVIN("volts per degree kelvin"),
        VOLTS_PER_METER("volts per meter"),
        DEGREES_PHASE("degrees phase"),
        POWER_FACTOR("power factor"),
        WEBERS("webers");

        private final String name;

        Electrical(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Energy implements DictionaryObject {
        JOULES("joules"),
        KILOJOULES("kilojoules"),
        KILOJOULES_PER_KILOGRAM("kilojoules per kilogram"),
        MEGAJOULES("megajoules"),
        WATT_HOURS("watt hours"),
        KILOWATT_HOURS("kilowatt hours"),
        MEGAWATT_HOURS("megawatt hours"),
        BTUS("btus"),
        KILO_BTUS("kilo btus"),
        MEGA_BTUS("mega btus"),
        THERMS("therms"),
        TON_HOURS("ton hours");

        private final String name;

        Energy(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Enthalpy implements DictionaryObject {
        JOULES_PER_KILOGRAM_DRY_AIR("joules per kilogram dry air"),
        KILOJOULES_PER_KILOGRAM_DRY_AIR("kilojoules per kilogram dry air"),
        MEGAJOULES_PER_KILOGRAM_DRY_AIR("megajoules per kilogram dry air"),
        BTUS_PER_POUND_DRY_AIR("btus per pound dry air"),
        BTUS_PER_POUND("btus per pound");

        private final String name;

        Enthalpy(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Entropy implements DictionaryObject {
        JOULES_PER_DEGREE_KELVIN("joules per degree kelvin"),
        KILOJOULES_PER_DEGREE_KELVIN("kilojoules per degree kelvin"),
        MEGAJOULES_PER_DEGREE_KELVIN("megajoules per degree kelvin"),
        JOULES_PER_KILOGRAM_DEGREE_KELVIN("joules per kilogram degree kelvin");

        private final String name;

        Entropy(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Force implements DictionaryObject {
        NEWTON("newton");

        private final String name;

        Force(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Frequency implements DictionaryObject {
        CYCLES_PER_HOUR("cycles per hour"),
        CYCLES_PER_MINUTE("cycles per minute"),
        HERTZ("hertz"),
        KILOHERTZ("kilohertz"),
        MEGAHERTZ("megahertz"),
        PER_HOUR("per hour");

        private final String name;

        Frequency(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Humidity implements DictionaryObject {
        GRAMS_OF_WATER_PER_KILOGRAM_DRY_AIR("grams of water per kilogram dry air"),
        PERCENT_RELATIVE_HUMIDITY("percent relative humidity");

        private final String name;

        Humidity(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Length implements DictionaryObject {
        MILLIMETERS("millimeters"),
        CENTIMETERS("centimeters"),
        METERS("meters"),
        INCHES("inches"),
        FEET("feet");

        private final String name;

        Length(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Light implements DictionaryObject {
        CANDELAS("candelas"),
        CANDELAS_PER_SQUARE_METER("candelas per square meter"),
        WATTS_PER_SQUARE_FOOT("watts per square foot"),
        WATTS_PER_SQUARE_METER("watts per square meter"),
        LUMENS("lumens"),
        LUXES("luxes"),
        FOOT_CANDLES("foot candles");

        private final String name;

        Light(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Mass implements DictionaryObject {
        KILOGRAMS("kilograms"),
        POUNDS_MASS("pounds mass"),
        TONS("tons");

        private final String name;

        Mass(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Massflow implements DictionaryObject {
        GRAMS_PER_SECOND("grams per second"),
        GRAMS_PER_MINUTE("grams per minute"),
        KILOGRAMS_PER_SECOND("kilograms per second"),
        KILOGRAMS_PER_MINUTE("kilograms per minute"),
        KILOGRAMS_PER_HOUR("kilograms per hour"),
        POUNDS_MASS_PER_SECOND("pounds mass per second"),
        POUNDS_MASS_PER_MINUTE("pounds mass per minute"),
        POUNDS_MASS_PER_HOUR("pounds mass per hour"),
        TONS_PER_HOUR("tons per hour");

        private final String name;

        Massflow(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Power implements DictionaryObject {
        MILLIWATTS("milliwatts"),
        WATTS("watts"),
        KILOWATTS("kilowatts"),
        MEGAWATTS("megawatts"),
        BTUS_PER_HOUR("btus per hour"),
        KILO_BTUS_PER_HOUR("kilo btus per hour"),
        HORSEPOWER("horsepower"),
        TONS_REFRIGERATION("tons refrigeration");

        private final String name;

        Power(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Pressure implements DictionaryObject {
        PASCALS("pascals"),
        HECTOPASCALS("hectopascals"),
        KILOPASCALS("kilopascals"),
        MILLIBARS("millibars"),
        BARS("bars"),
        POUNDS_FORCE_PER_SQUARE_INCH("pounds force per square inch"),
        CENTIMETERS_OF_WATER("centimeters of water"),
        INCHES_OF_WATER("inches of water"),
        MILLIMETERS_OF_MERCURY("millimeters of mercury"),
        CENTIMETERS_OF_MERCURY("centimeters of mercury"),
        INCHES_OF_MERCURY("inches of mercury");

        private final String name;

        Pressure(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Temperature implements DictionaryObject {
        DEGREES_CELSIUS("degrees celsius"),
        DEGREES_KELVIN("degrees kelvin"),
        DEGREES_KELVIN_PER_HOUR("degrees kelvin per hour"),
        DEGREES_KELVIN_PER_MINUTE("degrees kelvin per minute"),
        DEGREES_FAHRENHEIT("degrees fahrenheit"),
        DEGREE_DAYS_CELSIUS("degree days celsius"),
        DEGREE_DAYS_FAHRENHEIT("degree days fahrenheit"),
        DELTA_DEGREES_FAHRENHEIT("delta degrees fahrenheit"),
        DELTA_DEGREES_KELVIN("delta degrees kelvin");

        private final String name;

        Temperature(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Time implements DictionaryObject {
        YEARS("years"),
        MONTHS("months"),
        WEEKS("weeks"),
        DAYS("days"),
        HOURS("hours"),
        MINUTES("minutes"),
        SECONDS("seconds"),
        HUNDREDTHS_SECONDS("hundredths seconds"),
        MILLISECONDS("milliseconds");

        private final String name;

        Time(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Torque implements DictionaryObject {
        NEWTON_METERS("newton meters");

        private final String name;

        Torque(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Velocity implements DictionaryObject {
        MILLIMETERS_PER_SECOND("millimeters per second"),
        MILLIMETERS_PER_MINUTE("millimeters per minute"),
        METERS_PER_SECOND("meters per second"),
        METERS_PER_MINUTE("meters per minute"),
        METERS_PER_HOUR("meters per hour"),
        CUBIC_METERS_PER_MINUTE("cubic meters per minute"),
        FEET_PER_SECOND("feet per second"),
        FEET_PER_MINUTE("feet per minute"),
        MILES_PER_HOUR("miles per hour");

        private final String name;

        Velocity(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Volume implements DictionaryObject {
        CUBIC_FEET("cubic feet"),
        CUBIC_METERS("cubic meters"),
        IMPERIAL_GALLONS("imperial gallons"),
        LITERS("liters"),
        US_GALLONS("us gallons");

        private final String name;

        Volume(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum VolumetricFlow implements DictionaryObject {

        CUBIC_FEET_PER_SECOND("cubic feet per second"),
        CUBIC_FEET_PER_MINUTE("cubic feet per minute"),
        CUBIC_METERS_PER_SECOND("cubic meters per second"),
        CUBIC_METERS_PER_HOUR("cubic meters per hour"),
        IMPERIAL_GALLONS_PER_MINUTE("imperial gallons per minute"),
        LITERS_PER_SECOND("liters per second"),
        LITERS_PER_MINUTE("liters per minute"),
        LITERS_PER_HOUR("liters per hour"),
        US_GALLONS_PER_MINUTE("us gallons per minute");

        private final String name;

        VolumetricFlow(String name) {
            this.name = name;
        }
    };

    @Getter
    public enum Other implements DictionaryObject {
        DEGREES_ANGULAR("degrees angular"),
        DEGREES_CELSIUS_PER_HOUR("degrees celsius per hour"),
        DEGREES_CELSIUS_PER_MINUTE("degrees celsius per minute"),
        DEGREES_FAHRENHEIT_PER_HOUR("degrees fahrenheit per hour"),
        DEGREES_FAHRENHEIT_PER_MINUTE("degrees fahrenheit per minute"),
        JOULE_SECONDS("joule seconds"),
        KILOGRAMS_PER_CUBIC_METER("kilograms per cubic meter"),
        KILOWATT_HOURS_PER_SQUARE_METER("kilowatt hours per square meter"),
        KILOWATT_HOURS_PER_SQUARE_FOOT("kilowatt hours per square foot"),
        MEGAJOULES_PER_SQUARE_METER("megajoules per square meter"),
        MEGAJOULES_PER_SQUARE_FOOT("megajoules per square foot"),
        NO_UNITS("no units"),
        NEWTON_SECONDS("newton seconds"),
        NEWTONS_PER_METER("newtons per meter"),
        PARTS_PER_MILLION("parts per million"),
        PARTS_PER_BILLION("parts per billion"),
        PERCENT("percent"),
        PERCENT_OBSCURATION_PER_FOOT("percent obscuration per foot"),
        PERCENT_OBSCURATION_PER_METER("percent obscuration per meter"),
        PERCENT_PER_SECOND("percent per second"),
        PER_MINUTE("per minute"),
        PER_SECOND("per second"),
        PSI_PER_DEGREE_FAHRENHEIT("psi per degree fahrenheit"),
        RADIANS("radians"),
        RADIANS_PER_SECOND("radians per second"),
        REVOLUTIONS_PER_MINUTE("revolutions per minute"),
        SQUARE_METERS_PERNEWTON("square meters perNewton"),
        WATTS_PER_METER_PER_DEGREE_KELVIN("watts per meter per degree kelvin"),
        WATTS_PER_SQUARE_METER_DEGREE_KELVIN("watts per square meter degree kelvin");

        private final String name;

        Other(String name) {
            this.name = name;
        }
    };

    public static DictionaryObject getType(String groupName, String unitName) {
        logger.info("groupName: {}, unitName: {}", groupName, unitName);
        String className = _getClassName(groupName);
        Class<?> key = _getClass(className);
        return _getUnits(key, aggregate()).stream()
                .filter(unit -> _preparingUnitName(unit.getName())
                        .equalsIgnoreCase(_preparingUnitName(unitName.trim())))
                .findFirst()
                .orElse(Other.NO_UNITS);
    }

    private static List<DictionaryObject> _getUnits(Class<?> key, Map<Class<? extends DictionaryObject>, List<DictionaryObject>> map) {
        if(!map.containsKey(key)) {
            return map.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream())
                    .collect(Collectors.toList());
        }
        return map.get(key);
    }

    private static String _preparingUnitName(String unitName) {
        return unitName.replaceAll("\\s+", "_");
    }

    private static String _getClassName(String groupName) {
        return MessageFormat.format("{0}.{1}",EngineeringUnit.class.getPackage(), _splitCamelCase(groupName));
    }

    private static Class<?> _getClass(String groupName) {
        try {
            return Class.forName(groupName);
        } catch (ClassNotFoundException e) {
            logger.warn(e.getMessage());
            return Class.class;
        }
    }

    private static String _splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static Map<Class<? extends DictionaryObject>, List<DictionaryObject>> aggregate() {
        Map<Class<? extends DictionaryObject>, List<DictionaryObject>> map = new HashMap<>();

        map.put(Acceleration.class, _toList(Acceleration.values()));
        map.put(Currency.class, _toList(Currency.values()));
        map.put(Electrical.class, _toList(Electrical.values()));
        map.put(Area.class, _toList(Area.values()));
        map.put(Energy.class, _toList(Energy.values()));
        map.put(Enthalpy.class, _toList(Enthalpy.values()));
        map.put(Entropy.class, _toList(Entropy.values()));
        map.put(Force.class, _toList(Force.values()));
        map.put(Frequency.class, _toList(Frequency.values()));
        map.put(Humidity.class, _toList(Humidity.values()));

        map.put(Length.class, _toList(Length.values()));
        map.put(Light.class, _toList(Light.values()));
        map.put(Mass.class, _toList(Mass.values()));
        map.put(Massflow.class, _toList(Massflow.values()));
        map.put(Power.class, _toList(Power.values()));
        map.put(Pressure.class, _toList(Pressure.values()));
        map.put(Temperature.class, _toList(Temperature.values()));
        map.put(Time.class, _toList(Time.values()));
        map.put(Torque.class, _toList(Torque.values()));
        map.put(Velocity.class, _toList(Velocity.values()));
        map.put(Volume.class, _toList(Volume.values()));
        map.put(VolumetricFlow.class, _toList(VolumetricFlow.values()));
        map.put(Other.class, _toList(Other.values()));

        return map;
    }

    private static List<DictionaryObject> _toList(DictionaryObject... enumeration) {
        return Stream.of(enumeration).collect(Collectors.toList());
    }
}
