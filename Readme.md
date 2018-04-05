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



### 测试注意事项

#### 返回数据：

​	数据总条数 	total_num
 	合格条数		qualified_num
 	数据内容错误条数	content_error_num
 	数据格式错误条数	format_error_num
	csv内容统计数据	statistics

#### java测试文件要求：

​	一次测试过程中测试方法只能有一个
	被测试方法必须有一个 输入csv文件名 的参数
	被测试方法必须返回上述所列的数据，按照HashMap的方式返回