# Flow Log Ingester

A program that can parse a file containing flow log data, and maps each row to a tag based on a lookup table. 

The lookup table is defined as a csv file, and it has 3 columns, dstport,protocol,tag. The dstport and protocol combination decide what tag can be applied.

Note: The tags can map to more than one port, protocol combinations

## Architecture

The module is arranged as a pipeline.

There are four major components involved in this:
- FlowLogFileReader: A single producer thread which reads log record line-by-line from the file, and adds it to the queue for processing.
- FlowLogParser: Several consumer threads which reads the log from the queue and parse information
- FlowLogInsightsWriter: Fetches the insights from the data store and writes it to a output file.
- LogIngesterApplication: Glues above three components together to create a pipeline. It waits, using `CountDownLatch`, for the consumers to finish before starting with insights generation.

Apart from this,
- store.LookupTable contains parsed tags
- store.DataStore contains insights collected by the parsers.
- util.FlowLogFormatHelper is used to support custom log formats
- util.IanaProtocolHelper is used to convert between protocol number and protocol name

## Setup

- Java 17.0.2
- Maven 3.9.8

## Build

Generate the jar file using the below command.

`mvn clean package`

## Run

Before running the application, ensure that the required parameters are passed.

- `logFile` - Absolute path of the log file to be read
- `lookupTable` - Absolute path to the lookup table csv file
- `outputFile` - Absolute path to the output file where insights would be written
- `logFormat` (optional) - Ordered sequence of [available fields](https://docs.aws.amazon.com/vpc/latest/userguide/flow-log-records.html#flow-logs-fields) in the log file with each field separated by space. The default is v2 format (`version account-id interface-id srcaddr dstaddr srcport dstport protocol packets bytes start end action log-status`)

### FlowLog Format v2

`java -DlogFile="<>" -DlookupTable="<>" -DoutputFile="<>" -jar target/<fat_jar_name>-with-dependencies.jar`

### FlowLog Format (custom)

`java -DlogFile="<>" -DlookupTable="<>" -DoutputFile="<>" -DlogFormat="<space separated field with exact names>" -jar target/<fat_jar_name>-with-dependencies.jar`

## Sample Runs

### Run 1 (v2 flow log format)

`java  -DlogFile="path-to/log.txt" -DlookupTable="path-to/lookup.csv" -DoutputFile="path-to/output.txt" -jar target/LogIngester-1.0.0-jar-with-dependencies.jar`

#### log.txt
```log
2 123456789012 eni-0a1b2c3d 10.0.1.201 198.51.100.2 443 49153 6 25 20000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-4d3c2b1a 192.168.1.100 203.0.113.101 23 49154 6 15 12000 1620140761 1620140821 REJECT OK
2 123456789012 eni-5e6f7g8h 192.168.1.101 198.51.100.3 25 49155 6 10 8000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-9h8g7f6e 172.16.0.100 203.0.113.102 110 49156 6 12 9000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-7i8j9k0l 172.16.0.101 192.0.2.203 993 49157 6 8 5000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-6m7n8o9p 10.0.2.200 198.51.100.4 143 49158 6 18 14000 1620140761 1620140821 ACCEPT OK
2 123456789012 eni-1a2b3c4d 192.168.0.1 203.0.113.12 1024 80 6 10 5000 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-1a2b3c4d 203.0.113.12 192.168.0.1 80 1024 6 12 6000 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-1a2b3c4d 10.0.1.102 172.217.7.228 1030 443 6 8 4000 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-5f6g7h8i 10.0.2.103 52.26.198.183 56000 23 6 15 7500 1620140661 1620140721 REJECT OK
2 123456789012 eni-9k10l11m 192.168.1.5 51.15.99.115 49321 25 6 20 10000 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-1a2b3c4d 192.168.1.6 87.250.250.242 49152 110 6 5 2500 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-2d2e2f3g 192.168.2.7 77.88.55.80 49153 993 6 7 3500 1620140661 1620140721 ACCEPT OK
2 123456789012 eni-4h5i6j7k 172.16.0.2 192.0.2.146 49154 143 6 9 4500 1620140661 1620140721 ACCEPT OK
```

#### lookup.csv
```csv
dstport,protocol,tag
25,tcp,sv_P1
68,udp,sv_P2
23,tcp,sv_P1
31,udp,SV_P3
443,tcp,sv_P2
22,tcp,sv_P4
3389,tcp,sv_P5
0,icmp,sv_P5
110,tcp,email
993,tcp,email
143,tcp,email
```

#### output.txt
```text
Port/Protocol Combination Counts:
Port,Protocol,Count
993,tcp,1
1024,tcp,1
443,tcp,1
25,tcp,1
23,tcp,1
49153,tcp,1
49154,tcp,1
49155,tcp,1
49156,tcp,1
49157,tcp,1
80,tcp,1
143,tcp,1
49158,tcp,1
110,tcp,1

Tag Counts:
Tag,Count
sv_p2,1
untagged,8
sv_p1,2
email,3
```

### Run 2 (custom flow log format)

`java  -DlogFile="path-to/log.txt" -DlookupTable="path-to/lookup.csv" -DoutputFile="path-to/output.txt" -DlogFormat="account-id interface-id srcaddr dstaddr srcport dstport protocol packets bytes start end action log-status" -jar target/LogIngester-1.0.0-jar-with-dependencies.jar`

#### log.txt (version field not present)
```log
123456789012 eni-0a1b2c3d 10.0.1.201 198.51.100.2 443 49153 6 25 20000 1620140761 1620140821 ACCEPT OK
123456789012 eni-4d3c2b1a 192.168.1.100 203.0.113.101 23 49154 6 15 12000 1620140761 1620140821 REJECT OK
123456789012 eni-5e6f7g8h 192.168.1.101 198.51.100.3 25 49155 6 10 8000 1620140761 1620140821 ACCEPT OK
123456789012 eni-9h8g7f6e 172.16.0.100 203.0.113.102 110 49156 6 12 9000 1620140761 1620140821 ACCEPT OK
123456789012 eni-7i8j9k0l 172.16.0.101 192.0.2.203 993 49157 6 8 5000 1620140761 1620140821 ACCEPT OK
123456789012 eni-6m7n8o9p 10.0.2.200 198.51.100.4 143 49158 6 18 14000 1620140761 1620140821 ACCEPT OK
123456789012 eni-1a2b3c4d 192.168.0.1 203.0.113.12 1024 80 6 10 5000 1620140661 1620140721 ACCEPT OK
123456789012 eni-1a2b3c4d 203.0.113.12 192.168.0.1 80 1024 6 12 6000 1620140661 1620140721 ACCEPT OK
123456789012 eni-1a2b3c4d 10.0.1.102 172.217.7.228 1030 443 6 8 4000 1620140661 1620140721 ACCEPT OK
123456789012 eni-5f6g7h8i 10.0.2.103 52.26.198.183 56000 23 6 15 7500 1620140661 1620140721 REJECT OK
123456789012 eni-9k10l11m 192.168.1.5 51.15.99.115 49321 25 6 20 10000 1620140661 1620140721 ACCEPT OK
123456789012 eni-1a2b3c4d 192.168.1.6 87.250.250.242 49152 110 6 5 2500 1620140661 1620140721 ACCEPT OK
123456789012 eni-2d2e2f3g 192.168.2.7 77.88.55.80 49153 993 6 7 3500 1620140661 1620140721 ACCEPT OK
123456789012 eni-4h5i6j7k 172.16.0.2 192.0.2.146 49154 143 6 9 4500 1620140661 1620140721 ACCEPT OK
```

#### lookup.csv

```csv
dstport,protocol,tag
25,tcp,sv_P1
68,udp,sv_P2
23,tcp,sv_P1
31,udp,SV_P3
443,tcp,sv_P2
22,tcp,sv_P4
3389,tcp,sv_P5
0,icmp,sv_P5
110,tcp,email
993,tcp,email
143,tcp,email
```

#### output.txt

```text
Port/Protocol Combination Counts:
Port,Protocol,Count
993,tcp,1
1024,tcp,1
443,tcp,1
25,tcp,1
23,tcp,1
49153,tcp,1
49154,tcp,1
49155,tcp,1
49156,tcp,1
49157,tcp,1
80,tcp,1
143,tcp,1
49158,tcp,1
110,tcp,1

Tag Counts:
Tag,Count
sv_p2,1
untagged,8
sv_p1,2
email,3
```

## Assumptions

- Lookup Table contains correct protocol names which can be mapped to protocol number in the log record using IANA mappings
- Custom log format is supported when the field names are passed with the same name as in the Flow Log fields specification
- Given the constraints, a single application will be able to meet the requirements. Dependencies like Redis will improve the performance but is an overkill given the time constraints for the exercise.