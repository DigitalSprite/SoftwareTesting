## Readme

### 接口：

http://localhost:8080/file/upload/csv?name=...

- 作用：上传csv文件
- 参数：
  - name：csv的文件名字（可以和原始文件不同），不带后缀



http://localhost:8080/file/upload/java

- 作用：上传java文件并获取上传java文件中所有的类以及它的方法
- 返回值：HashMap



http://localhost:8080/file/download?name=...

- 作用：下载测试生成的csv文件
- 参数：
  - name：csv的文件名字（可以和原始文件不同），不带后缀



http://localhost:8080/test/csv?class=...&func=...&csv=...

- 作用：根据给出的类名、方法名和csv文件的名字测试java文件