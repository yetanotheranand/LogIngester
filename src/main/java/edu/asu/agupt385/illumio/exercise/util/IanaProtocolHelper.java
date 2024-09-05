package edu.asu.agupt385.illumio.exercise.util;

import java.util.HashMap;
import java.util.Map;

public class IanaProtocolHelper {
  // Mapping for https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml
  private static final Map<Integer, String> PROTOCOL_MAP = new HashMap<>(150);

  static {
    PROTOCOL_MAP.put(0, "HOPOPT");
    PROTOCOL_MAP.put(1, "ICMP");
    PROTOCOL_MAP.put(2, "IGMP");
    PROTOCOL_MAP.put(3, "GGP");
    PROTOCOL_MAP.put(4, "IP-in-IP");
    PROTOCOL_MAP.put(5, "ST");
    PROTOCOL_MAP.put(6, "TCP");
    PROTOCOL_MAP.put(7, "CBT");
    PROTOCOL_MAP.put(8, "EGP");
    PROTOCOL_MAP.put(9, "IGP");
    PROTOCOL_MAP.put(10, "BBN-RCC-MON");
    PROTOCOL_MAP.put(11, "NVP-II");
    PROTOCOL_MAP.put(12, "PUP");
    PROTOCOL_MAP.put(13, "ARGUS");
    PROTOCOL_MAP.put(14, "EMCON");
    PROTOCOL_MAP.put(15, "XNET");
    PROTOCOL_MAP.put(16, "CHAOS");
    PROTOCOL_MAP.put(17, "UDP");
    PROTOCOL_MAP.put(18, "MUX");
    PROTOCOL_MAP.put(19, "DCN-MEAS");
    PROTOCOL_MAP.put(20, "HMP");
    PROTOCOL_MAP.put(21, "PRM");
    PROTOCOL_MAP.put(22, "XNS-IDP");
    PROTOCOL_MAP.put(23, "TRUNK-1");
    PROTOCOL_MAP.put(24, "TRUNK-2");
    PROTOCOL_MAP.put(25, "LEAF-1");
    PROTOCOL_MAP.put(26, "LEAF-2");
    PROTOCOL_MAP.put(27, "RDP");
    PROTOCOL_MAP.put(28, "IRTP");
    PROTOCOL_MAP.put(29, "ISO-TP4");
    PROTOCOL_MAP.put(30, "NETBLT");
    PROTOCOL_MAP.put(31, "MFE-NSP");
    PROTOCOL_MAP.put(32, "MERIT-INP");
    PROTOCOL_MAP.put(33, "DCCP");
    PROTOCOL_MAP.put(34, "3PC");
    PROTOCOL_MAP.put(35, "IDPR");
    PROTOCOL_MAP.put(36, "XTP");
    PROTOCOL_MAP.put(37, "DDP");
    PROTOCOL_MAP.put(38, "IDPR-CMTP");
    PROTOCOL_MAP.put(39, "TP++");
    PROTOCOL_MAP.put(40, "IL");
    PROTOCOL_MAP.put(41, "IPv6");
    PROTOCOL_MAP.put(42, "SDRP");
    PROTOCOL_MAP.put(43, "IPv6-Route");
    PROTOCOL_MAP.put(44, "IPv6-Frag");
    PROTOCOL_MAP.put(45, "IDRP");
    PROTOCOL_MAP.put(46, "RSVP");
    PROTOCOL_MAP.put(47, "GRE");
    PROTOCOL_MAP.put(48, "DSR");
    PROTOCOL_MAP.put(49, "BNA");
    PROTOCOL_MAP.put(50, "ESP");
    PROTOCOL_MAP.put(51, "AH");
    PROTOCOL_MAP.put(52, "I-NLSP");
    PROTOCOL_MAP.put(53, "SWIPE");
    PROTOCOL_MAP.put(54, "NARP");
    PROTOCOL_MAP.put(55, "MOBILE");
    PROTOCOL_MAP.put(56, "TLSP");
    PROTOCOL_MAP.put(57, "SKIP");
    PROTOCOL_MAP.put(58, "IPv6-ICMP");
    PROTOCOL_MAP.put(59, "IPv6-NoNxt");
    PROTOCOL_MAP.put(60, "IPv6-Opts");
    PROTOCOL_MAP.put(62, "CFTP");
    PROTOCOL_MAP.put(64, "SAT-EXPAK");
    PROTOCOL_MAP.put(65, "KRYPTOLAN");
    PROTOCOL_MAP.put(66, "RVD");
    PROTOCOL_MAP.put(67, "IPPC");
    PROTOCOL_MAP.put(69, "SAT-MON");
    PROTOCOL_MAP.put(70, "VISA");
    PROTOCOL_MAP.put(71, "IPCV");
    PROTOCOL_MAP.put(72, "CPNX");
    PROTOCOL_MAP.put(73, "CPHB");
    PROTOCOL_MAP.put(74, "WSN");
    PROTOCOL_MAP.put(75, "PVP");
    PROTOCOL_MAP.put(76, "BR-SAT-MON");
    PROTOCOL_MAP.put(77, "SUN-ND");
    PROTOCOL_MAP.put(78, "WB-MON");
    PROTOCOL_MAP.put(79, "WB-EXPAK");
    PROTOCOL_MAP.put(80, "ISO-IP");
    PROTOCOL_MAP.put(81, "VMTP");
    PROTOCOL_MAP.put(82, "SECURE-VMTP");
    PROTOCOL_MAP.put(83, "VINES");
    PROTOCOL_MAP.put(84, "TTP");
    PROTOCOL_MAP.put(85, "NSFNET-IGP");
    PROTOCOL_MAP.put(86, "DGP");
    PROTOCOL_MAP.put(87, "TCF");
    PROTOCOL_MAP.put(88, "EIGRP");
    PROTOCOL_MAP.put(89, "OSPF");
    PROTOCOL_MAP.put(90, "Sprite-RPC");
    PROTOCOL_MAP.put(91, "LARP");
    PROTOCOL_MAP.put(92, "MTP");
    PROTOCOL_MAP.put(93, "AX.25");
    PROTOCOL_MAP.put(94, "IPIP");
    PROTOCOL_MAP.put(95, "MICP");
    PROTOCOL_MAP.put(96, "SCC-SP");
    PROTOCOL_MAP.put(97, "ETHERIP");
    PROTOCOL_MAP.put(98, "ENCAP");
    PROTOCOL_MAP.put(100, "GMTP");
    PROTOCOL_MAP.put(101, "IFMP");
    PROTOCOL_MAP.put(102, "PNNI");
    PROTOCOL_MAP.put(103, "PIM");
    PROTOCOL_MAP.put(104, "ARIS");
    PROTOCOL_MAP.put(105, "SCPS");
    PROTOCOL_MAP.put(106, "QNX");
    PROTOCOL_MAP.put(107, "A/N");
    PROTOCOL_MAP.put(108, "IPComp");
    PROTOCOL_MAP.put(109, "SNP");
    PROTOCOL_MAP.put(110, "Compaq-Peer");
    PROTOCOL_MAP.put(111, "IPX-in-IP");
    PROTOCOL_MAP.put(112, "VRRP");
    PROTOCOL_MAP.put(113, "PGM");
    PROTOCOL_MAP.put(114, "any 0-hop");
    PROTOCOL_MAP.put(115, "L2TP");
    PROTOCOL_MAP.put(116, "DDX");
    PROTOCOL_MAP.put(117, "IATP");
    PROTOCOL_MAP.put(118, "STP");
    PROTOCOL_MAP.put(119, "SRP");
    PROTOCOL_MAP.put(120, "UTI");
    PROTOCOL_MAP.put(121, "SMP");
    PROTOCOL_MAP.put(122, "SM");
    PROTOCOL_MAP.put(123, "PTP");
    PROTOCOL_MAP.put(124, "ISIS over IPv4");
    PROTOCOL_MAP.put(125, "FIRE");
    PROTOCOL_MAP.put(126, "CRTP");
    PROTOCOL_MAP.put(127, "CRUDP");
    PROTOCOL_MAP.put(128, "SSCOPMCE");
    PROTOCOL_MAP.put(129, "IPLT");
    PROTOCOL_MAP.put(130, "SPS");
    PROTOCOL_MAP.put(131, "PIPE");
    PROTOCOL_MAP.put(132, "SCTP");
    PROTOCOL_MAP.put(133, "FC");
    PROTOCOL_MAP.put(134, "RSVP-E2E-IGNORE");
    PROTOCOL_MAP.put(135, "Mobility Header");
    PROTOCOL_MAP.put(136, "UDPLite");
    PROTOCOL_MAP.put(137, "MPLS-in-IP");
    PROTOCOL_MAP.put(138, "manet");
    PROTOCOL_MAP.put(139, "HIP");
    PROTOCOL_MAP.put(140, "Shim6");
    PROTOCOL_MAP.put(141, "WESP");
    PROTOCOL_MAP.put(142, "ROHC");
    PROTOCOL_MAP.put(143, "Ethernet");
    PROTOCOL_MAP.put(144, "AGGFRAG");
    PROTOCOL_MAP.put(145, "NSH");
    PROTOCOL_MAP.put(255, "Reserved");
  }

  public static String getProtocolName(int protocolNumber) {
    return PROTOCOL_MAP.getOrDefault(protocolNumber, "Unknown").toLowerCase();
  }

  public static int getProtocolNumber(String protocolName) throws Exception {
    for (Map.Entry<Integer, String> entry : PROTOCOL_MAP.entrySet()) {
      if (entry.getValue().equalsIgnoreCase(protocolName)) {
        return entry.getKey();
      }
    }
    throw new Exception("Missing protocol name and number mapping for " + protocolName);
  }
}
