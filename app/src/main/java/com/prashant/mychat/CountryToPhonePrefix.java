package com.prashant.mychat;

import java.util.HashMap;
import java.util.Map;

public class CountryToPhonePrefix {
    public static String getPhone(String code) {
        return country2phone.get(code.toUpperCase());
    }
    public static Map<String, String> getAll(){
        return country2phone;
    }
    private static Map<String, String> country2phone = new HashMap<String, String>();
    static {
        country2phone.put("AF", "+93");
        country2phone.put("AL", "+355");
        country2phone.put("DZ", "+213");
        country2phone.put("AD", "+376");
        country2phone.put("AO", "+244");
        country2phone.put("AR", "+54");
        country2phone.put("AM", "+374");
        country2phone.put("AU", "+61");
        country2phone.put("AT", "+43");
        country2phone.put("AZ", "+994");
        country2phone.put("BH", "+973");
        country2phone.put("BD", "+880");
        country2phone.put("BY", "+375");
        country2phone.put("BE", "+32");
        country2phone.put("BZ", "+501");
        country2phone.put("BJ", "+229");
        country2phone.put("BT", "+975");
        country2phone.put("BO", "+591");
        country2phone.put("BA", "+387");
        country2phone.put("BW", "+267");
        country2phone.put("BR", "+55");
        country2phone.put("BN", "+673");
        country2phone.put("BG", "+359");
        country2phone.put("BF", "+226");
        country2phone.put("BI", "+257");
        country2phone.put("KH", "+855");
        country2phone.put("CM", "+237");
        country2phone.put("CA", "+1");
        country2phone.put("CV", "+238");
        country2phone.put("CF", "+236");
        country2phone.put("TD", "+235");
        country2phone.put("CL", "+56");
        country2phone.put("CN", "+86");
        country2phone.put("CO", "+57");
        country2phone.put("KM", "+269");
        country2phone.put("CD", "+243");
        country2phone.put("CG", "+242");
        country2phone.put("CR", "+506");
        country2phone.put("CI", "+225");
        country2phone.put("HR", "+385");
        country2phone.put("CU", "+53");
        country2phone.put("CY", "+357");
        country2phone.put("CZ", "+420");
        country2phone.put("DK", "+45");
        country2phone.put("DJ", "+253");
        country2phone.put("EC", "+593");
        country2phone.put("EG", "+20");
        country2phone.put("SV", "+503");
        country2phone.put("GQ", "+240");
        country2phone.put("ER", "+291");
        country2phone.put("EE", "+372");
        country2phone.put("ET", "+251");
        country2phone.put("FJ", "+679");
        country2phone.put("FI", "+358");
        country2phone.put("FR", "+33");
        country2phone.put("GA", "+241");
        country2phone.put("GM", "+220");
        country2phone.put("GE", "+995");
        country2phone.put("DE", "+49");
        country2phone.put("GH", "+233");
        country2phone.put("GR", "+30");
        country2phone.put("GT", "+502");
        country2phone.put("GN", "+224");
        country2phone.put("GW", "+245");
        country2phone.put("GY", "+592");
        country2phone.put("HT", "+509");
        country2phone.put("HN", "+504");
        country2phone.put("HU", "+36");
        country2phone.put("IS", "+354");
        country2phone.put("IN", "+91");
        country2phone.put("ID", "+62");
        country2phone.put("IR", "+98");
        country2phone.put("IQ", "+964");
        country2phone.put("IE", "+353");
        country2phone.put("IL", "+972");
        country2phone.put("IT", "+39");
        country2phone.put("JP", "+81");
        country2phone.put("JO", "+962");
        country2phone.put("KZ", "+7");
        country2phone.put("KE", "+254");
        country2phone.put("KI", "+686");
        country2phone.put("KP", "+850");
        country2phone.put("KW", "+965");
        country2phone.put("KG", "+996");
        country2phone.put("LA", "+856");
        country2phone.put("LV", "+371");
        country2phone.put("LB", "+961");
        country2phone.put("LS", "+266");
        country2phone.put("LR", "+231");
        country2phone.put("LY", "+218");
        country2phone.put("LI", "+423");
        country2phone.put("LT", "+370");
        country2phone.put("LU", "+352");
        country2phone.put("MK", "+389");
        country2phone.put("MG", "+261");
        country2phone.put("MW", "+265");
        country2phone.put("MY", "+60");
        country2phone.put("MV", "+960");
        country2phone.put("ML", "+223");
        country2phone.put("MT", "+356");
        country2phone.put("MH", "+692");
        country2phone.put("MQ", "+596"); // Martinique
        country2phone.put("MR", "+222"); // Mauritania
        country2phone.put("MU", "+230"); // Mauritius
        country2phone.put("YT", "+262"); // Mayotte
        country2phone.put("MX", "+52");  // Mexico
        country2phone.put("FM", "+691"); // Micronesia
        country2phone.put("MD", "+373"); // Moldova
        country2phone.put("MC", "+377"); // Monaco
        country2phone.put("MN", "+976"); // Mongolia
        country2phone.put("ME", "+382"); // Montenegro
        country2phone.put("MS", "+1");   // Montserrat
        country2phone.put("MA", "+212"); // Morocco
        country2phone.put("MZ", "+258"); // Mozambique
        country2phone.put("MM", "+95");  // Myanmar
        country2phone.put("NA", "+264"); // Namibia
        country2phone.put("NR", "+674"); // Nauru
        country2phone.put("NP", "+977"); // Nepal
        country2phone.put("NL", "+31");  // Netherlands
        country2phone.put("NC", "+687"); // New Caledonia
        country2phone.put("NZ", "+64");  // New Zealand
        country2phone.put("NI", "+505"); // Nicaragua
        country2phone.put("NE", "+227"); // Niger
        country2phone.put("NG", "+234"); // Nigeria
        country2phone.put("NU", "+683"); // Niue
        country2phone.put("NF", "+672"); // Norfolk Island
        country2phone.put("MP", "+1");   // Northern Mariana Islands
        country2phone.put("NO", "+47");  // Norway
        country2phone.put("OM", "+968"); // Oman
        country2phone.put("PK", "+92");  // Pakistan
        country2phone.put("PW", "+680"); // Palau
        country2phone.put("PS", "+970"); // Palestine
        country2phone.put("PA", "+507"); // Panama
        country2phone.put("PG", "+675"); // Papua New Guinea
        country2phone.put("PY", "+595"); // Paraguay
        country2phone.put("PE", "+51");  // Peru
        country2phone.put("PH", "+63");  // Philippines
        country2phone.put("PN", "+64");  // Pitcairn Islands
        country2phone.put("PL", "+48");  // Poland
        country2phone.put("PT", "+351"); // Portugal
        country2phone.put("PR", "+1");   // Puerto Rico
        country2phone.put("QA", "+974"); // Qatar
        country2phone.put("RE", "+262"); // Réunion
        country2phone.put("RO", "+40");  // Romania
        country2phone.put("RU", "+7");   // Russia
        country2phone.put("RW", "+250"); // Rwanda
        country2phone.put("BL", "+590"); // Saint Barthélemy
        country2phone.put("SH", "+290"); // Saint Helena
        country2phone.put("KN", "+1");   // Saint Kitts and Nevis
        country2phone.put("LC", "+1");   // Saint Lucia
        country2phone.put("MF", "+590"); // Saint Martin
        country2phone.put("PM", "+508"); // Saint Pierre and Miquelon
        country2phone.put("VC", "+1");   // Saint Vincent and the Grenadines
        country2phone.put("WS", "+685"); // Samoa
        country2phone.put("SM", "+378"); // San Marino
        country2phone.put("ST", "+239"); // São Tomé and Príncipe
        country2phone.put("SA", "+966"); // Saudi Arabia
        country2phone.put("SN", "+221"); // Senegal
        country2phone.put("RS", "+381"); // Serbia
        country2phone.put("SC", "+248"); // Seychelles
        country2phone.put("SL", "+232"); // Sierra Leone
        country2phone.put("SG", "+65");  // Singapore
        country2phone.put("SX", "+1");   // Sint Maarten
        country2phone.put("SK", "+421"); // Slovakia
        country2phone.put("SI", "+386"); // Slovenia
        country2phone.put("SB", "+677"); // Solomon Islands
        country2phone.put("SO", "+252"); // Somalia
        country2phone.put("ZA", "+27");  // South Africa
        country2phone.put("GS", "+500"); // South Georgia and the South Sandwich Islands
        country2phone.put("KR", "+82");  // South Korea
        country2phone.put("SS", "+211"); // South Sudan
        country2phone.put("ES", "+34");  // Spain
        country2phone.put("LK", "+94");  // Sri Lanka
        country2phone.put("SD", "+249"); // Sudan
        country2phone.put("SR", "+597"); // Suriname
        country2phone.put("SJ", "+47");  // Svalbard and Jan Mayen
        country2phone.put("SZ", "+268"); // Swaziland
        country2phone.put("SE", "+46");  // Sweden
        country2phone.put("CH", "+41");  // Switzerland
        country2phone.put("SY", "+963"); // Syria
        country2phone.put("TW", "+886"); // Taiwan
        country2phone.put("TJ", "+992"); // Tajikistan
        country2phone.put("TZ", "+255"); // Tanzania
        country2phone.put("TH", "+66");  // Thailand
        country2phone.put("TL", "+670"); // Timor-Leste
        country2phone.put("TG", "+228"); // Togo
        country2phone.put("TK", "+690"); // Tokelau
        country2phone.put("TO", "+676"); // Tonga
        country2phone.put("TT", "+1");   // Trinidad and Tobago
        country2phone.put("TN", "+216"); // Tunisia
        country2phone.put("TR", "+90");  // Turkey
        country2phone.put("TM", "+993"); // Turkmenistan
        country2phone.put("TC", "+1");   // Turks and Caicos Islands
        country2phone.put("TV", "+688"); // Tuvalu
        country2phone.put("UG", "+256"); // Uganda
        country2phone.put("UA", "+380"); // Ukraine
        country2phone.put("AE", "+971"); // United Arab Emirates
        country2phone.put("GB", "+44");  // United Kingdom
        country2phone.put("US", "+1");   // United States
        country2phone.put("UM", "+1");   // United States Minor Outlying Islands
        country2phone.put("UY", "+598"); // Uruguay
        country2phone.put("UZ", "+998"); // Uzbekistan
        country2phone.put("VU", "+678"); // Vanuatu
        country2phone.put("VA", "+39");  // Vatican City
        country2phone.put("VE", "+58");  // Venezuela
        country2phone.put("VN", "+84");  // Vietnam
        country2phone.put("VG", "+1");   // British Virgin Islands
        country2phone.put("VI", "+1");   // U.S. Virgin Islands
        country2phone.put("WF", "+681"); // Wallis and Futuna
        country2phone.put("EH", "+212"); // Western Sahara
        country2phone.put("YE", "+967"); // Yemen
        country2phone.put("ZM", "+260"); // Zambia
        country2phone.put("ZW", "+263"); // Zimbabwe
    }
}