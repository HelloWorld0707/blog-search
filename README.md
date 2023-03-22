# blog-search
블로그 
Java11, Gradle, H2

# API

### URL

```
/search/blog
```
### Request field

| Parameter | type | Description | required | default |
| ------ | ------ | ------ | ------ | ------ |
| query | string | 검색어 | **ture** | - |
| sort | string | 결과 문서 정렬 방식, `accuracy`(정확도순) or `recency`(최신순) | false | `accuracy` |
| page | integer | 결과 페이지 번호, 1~50 사이의 값 | false | `1` |
| size | integer | 한 페이지에 보여질 문서 수, 1~50 사이의 값 | false | `10` |


### Response field

| Parameter | type | Description |
| ------ | ------ | ------ |
| total_count | integer | |
| | | |
| is_end | boolean | |
|  | | 
| | | 

### Response sample
```
{
  "sample": "test"
}
```

---

```
/search/rank
```

| Parameter | type | Description | required | default |
| ------ | ------ | ------ | ------ | ------ |
| rank | Integer | 표시할 키워드의 수, 1~20 사이의 값 | false | 10 |

