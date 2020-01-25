## Serialization Simple Comparison

So this is sort of a comparision between CSV, JSON, ProtoBuf, and Avro serialization and deserialization speeds and compression sizes.

Basically I produce 1000 strings and numbers, and create the most simple of all schemas for each protocol. Then, for 1000 times, I 
encoded that data and decoded it and recorded the average times.


<details>
<summary>CSV Schema</summary>

  ```
  name,number
  randomName,1234
  ...
  ```

</details>
<details>
<summary>JSON Schema</summary>

  ```
  {
    "name": [
      "randomName",
      ...
    ],
    "number": [
      1234,
      ...
    ]
  }
  ```
  
</details>
<details>
<summary>ProtoBuf Schema</summary>

  ```
  message Wrapper {
      repeated string name = 1;
      repeated int32 number = 2;
  }
  ```

</details>
<details>
<summary>Avro Schema</summary>

  ```
  {
      "namespace": "",
      "name": "AvroSample",
      "type": "record",
      "fields": [
          {
              "name": "name",
              "type": {
                  "name": "nameType",
                  "type": "array",
                  "items": "string"
              }
          },
          {
              "name": "number",
              "type": {
                  "name": "numberType",
                  "type": "array",
                  "items": "int"
              }
          }
      ]
  }
  ```

</details>

In the end, the results were clear. Avro came out on top for encoding
time and data size, but Protobuf was really fast at parsing time. This of course could be due to the data types I used. To do a much better comparison, I'd have to include objects, enums, and nested
arrays (etc.)

```
==| CSV |==
Data Size: 1024302 bytes
Base64 Size: 1365736 bytes
Encode Time: 4.538122835 ms
Parse Time: 9.893573074999999 ms

==| JSON |==
Data Size: 1268814 bytes
Base64 Size: 1691752 bytes
Encode Time: 8.921927827 ms
Parse Time: 8.213622645 ms

==| ProtoBuf |==
Data Size: 936944 bytes
Base64 Size: 1249260 bytes
Encode Time: 5.467107863 ms
Parse Time: 1.2513737409999999 ms

==| Avro |==
Data Size: 847351 bytes
Base64 Size: 1129804 bytes
Encode Time: 4.372638659 ms
Parse Time: 2.6514886630000003 ms
```
