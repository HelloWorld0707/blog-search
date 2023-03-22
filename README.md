# Installation
[Google Drive Link](https://drive.google.com/file/d/1qT0vZSo5_PhHRGbSDOrK71YD_1qv_Nqg/view?usp=share_link)

# API

### URL
```
GET /search/rank
/search/blog
```
 - 키워드를 통해 검색된 블로그.
 - Kakao API에 장애가 발생한 경우, 네이버 블로그 검색 API를 호출합니다.

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
| page_number | integer | 결과 페이지 번호 |
| page_size | integer | 한 페이지에 보여지는 문서 수 |
| total_size | integer | 전체 페이지 수 |
| domain | 호출된 API (Kakao, Naver) |
| is_end | boolean | 마지막 페이지 여부 |

### Response sample
```
{
   "page_number":1,
   "page_size":10,
   "total_size":799,
   "result":[
      {
         "title":"iOS Swift <b>카카오</b> 소셜 로그인(<b>Kakao</b> Social Login)",
         "contents":"<b>Kakao</b> 로그인 우선 <b>카카오</b> 로그인은...",
         "url":"http://s2ung.tistory.com/32",
         "blog_name":"Seung",
         "thumbnail":"https://search3.kakaocdn.net/argon/130x130_85_c/2jmFYVaxz5n",
         "datetime":"2023-02-13T18:56:19.000+09:00"
      }
      
      ...
   ],
   "domain":"KAKAO",
   "is_end":false
}
```

---
```
GET /search/rank
Host: localhost:8080
```
 - 사용자들이 많이 검색한 순서대로, 1~20개의 검색 키워드를 제공합니다.

### Request field
| Parameter | type | Description | required | default |
| ------ | ------ | ------ | ------ | ------ |
| size | Integer | 표시할 키워드의 수, 1~20 사이의 값 | false | 10 |

### Response field
| Parameter | type | Description |
| ------ | ------ | ------ |
| query | string | 검색된 쿼리 |
| view | long | 검색된 횟수 |

### Response Example
```
[{"query":"kakao","view":151},{"query":"kakao bank","view":151}]
```

# Architecture
![123 drawio](https://user-images.githubusercontent.com/19347104/226846189-dcebcefc-0675-474a-9194-ac919e8e663b.png)

# spec
Java11, Gradle, H2 DB
