#[Spring & REST](https://github.com/qala-io/java-course/blob/master/docs/programme/spring-n-rest.md)
## HTTP
1. [network protocols layers](https://github.com/qala-io/java-course/blob/master/docs/programme/articles/networking-layers.md)
2. [Fully qualified domain name resolving](https://github.com/qala-io/java-course/blob/master/docs/programme/articles/dns.md)
3. HTTP methods:

    Method|[Safe](https://developer.mozilla.org/en-US/docs/Glossary/safe)|[Idempotent](https://developer.mozilla.org/en-US/docs/Glossary/idempotent)|[Cacheable](https://developer.mozilla.org/en-US/docs/Glossary/cacheable)|[Description](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods)
    :---:|:---:|:---:|:---:|:--------:
    `GET`|:heavy_check_mark:|:heavy_check_mark:|:heavy_check_mark:|requests a representation of the specified resource
    `HEAD`|:heavy_check_mark:|:heavy_check_mark:|:heavy_check_mark:|like GET request, but without the response body
    `POST`|:x:|:x:|:white_check_mark:|used to submit an entity to the specified resource, often causing a change in state or side effects on the server
    `PUT`|:x:|:heavy_check_mark:|:x:|replaces all current representations of the target resource with the request payload
    `DELETE`|:x:|:heavy_check_mark:|:x:|deletes the specified resource
    `CONNECT`|-|-|-|establishes a tunnel to the server identified by the target resource
    `OPTIONS`|:heavy_check_mark:|:heavy_check_mark:|-|used to describe the communication options for the target resource
    `TRACE`|-|-|-|performs a message loop-back test along the path to the target resource
    `PATCH`|:x:|-|:white_check_mark:|used to apply partial modifications to a resource

4. Multipurpose Internet Mail Extensions or [MIME type](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types)



