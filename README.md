## SandBox FIS

### API

### 1. 채널 등록
|기능|메소드|URL|
------|-----|----|
채널등록 | POST | /api/v1/channels|


요청

|필드명|타입|설명|
------|-----|----|
channel_name | String | 채널명 |
channel_email | String | 이메일 |

```json
POST /api/v1/channels

{
    "channel_name" : "채널입니다.",
    "channel_email" : "email@email.com"
}
```

응답

|필드명 | 타입 | 설명 |
--------|------|-----|
channel_id | Long | 채널ID |

```json
{
    "channel_id": 1
}
```

### 2. 크리에이터 등록
|기능|메소드|URL|
------|-----|----|
크리에이터등록 | POST | /api/v1/creators|

요청

|필드명|타입|설명|
------|-----|----|
creator_name | String | 크리에이터명 |
creator_email | String | 이메일 |

```json
POST /api/v1/creators

{
    "creator_name" : "크리에이터1",
    "creator_email" : "email1@email.com"
}
```

응답

|필드명 | 타입 | 설명 |
--------|------|-----|
creator_id | Long | 크리에이터ID |

```json
{
    "creator_id": 1
}
```

### 3. 계약서 등록
|기능|메소드|URL|
------|-----|----|
계약서 등록 | POST | /api/v1/contracts|

요청

|필드명|타입|설명|
------|-----|----|
company | Object | 회사 계약 조건 |
company > channel_id | Long | 채널ID |
company > company_rs | Integer | 회사RS |
company > creators_rs | Integer | 크리에이터RS |
creators | List<<z>Object> | 크리에이터 계약 조건 |
creators > creator_id | Long | 크리에이터 ID |
creators > creator_rs | Integer | 크리에이터별 RS |

```json
POST /api/v1/contracts

{
    "company" : {
        "channel_id" : 1,
        "company_rs" : 50,
        "creators_rs" : 50
    },
    "creators" : [
        {
            "creator_id" : 1,
            "creator_rs" : 50
        },
        {
            "creator_id" : 2,
            "creator_rs" : 50
        }
    ]
}
```

응답

|필드명 | 타입 | 설명 |
--------|------|-----|
contract_id | Long | 계약서ID |

```json
{
    "contract_id": 1
}
```

### 4. 정산금액 저장
|기능|메소드|URL|
------|-----|----|
정산금액 저장 | POST | /api/v1/amounts|

요청

|필드명|타입|설명|
------|-----|----|
contract_id | Long | 계약서ID |
day | String | 정산날짜 |
amount | BigDecimal | 정산금액 |

```json
POST /api/v1/amounts

{
    "contract_id" : 1,
    "day" : "2022-04-06",
    "amount" : 12442.11
}
```

응답

|필드명 | 타입 | 설명 |
--------|------|-----|
contract_id | Long | 계약서ID |

```json
{
    "contract_id": 1
}
```

### 5. 정산금액 조회(채널 크리에이터들 합계)
|기능|메소드|URL|
------|-----|----|
정산금액 조회<br>(채널 크리에이터들 합계) | GET | /api/v1/amounts/{contractId}/creators?<br>startMonth={startMonth}&endMonth={endMonth}|

요청

|필드명|타입|설명|
------|-----|----|
contractId | Long | 계약서ID |
startMonth | String | 시작월 |
endMonth | String | 종료월 |

```json
GET api/v1/amounts/1/creators?startMonth=2022-03&endMonth=2022-04
```

응답

|필드명|타입|설명|
------|-----|----|
contract_id | Long | 계약서ID |
creators_amounts | List | 크리에이터들 정산결과 |
creators_amounts > amount | BigDecimal | 정산 금액 |
creators_amounts > month | String | 정산월 |
```json
{
    "contract_id": 1,
    "creators_amounts": [
        {
            "amount": "12454.2800000",
            "month": "2022-04"
        },
        {
            "amount": "6282.1100000",
            "month": "2022-03"
        }
    ]
}
```

### 6. 정산금액 조회(채널 크레이이터 별 합계)
|기능|메소드|URL|
------|-----|----|
정산금액 조회<br>(채널 크리에이터 별 합계) | GET | /api/v1/amounts/{contractId}/creators/<br>{creatorId}?startMonth={startMonth}&endMonth={endMonth}|

요청

|필드명|타입|설명|
------|-----|----|
contractId | Long | 계약서ID |
creatorId | Long | 크리에이터ID|
startMonth | String | 시작월 |
endMonth | String | 종료월 |

```json
GET /api/v1/amounts/1/creators/1?startMonth=2022-03&endMonth=2022-04
```

응답

|필드명|타입|설명|
------|-----|----|
contract_id | Long | 계약서ID |
creator_id | Long | 크리에이터 ID |
creator_amounts | List | 크리에이터 정산 결과 |
creator_amounts > amount | BigDecimal | 정산금액 |
creator_amounts > month | String | 정산월 |
```json
{
    "contract_id": 1,
    "creator_id": 1,
    "creator_amounts": [
        {
            "amount": "6227.1400000",
            "month": "2022-04"
        },
        {
            "amount": "3141.0550000",
            "month": "2022-03"
        }
    ]
}
```

### 7. 정산금액 조회(회사)
|기능|메소드|URL|
------|-----|----|
정산금액 조회<br>(회사) | GET | /api/v1/amounts/{contractId}/companies?month={month}|

요청

|필드명|타입|설명|
------|-----|----|
contractId | Long | 계약서ID |
month | String | 검색월 |

```json
GET /api/v1/amounts/1/companies?month=2022-04
```

응답

|필드명|타입|설명|
------|-----|----|
contract_id | Long | 계약서ID |
month | String | 검색월 |
total_amount | BigDecimal | 정산금액 총매출 |
net_amount | BigDecimal | 정산금액 순매출 |
```json
{
    "contract_id": 1,
    "month": "2022-04",
    "total_amount": "24908.5600000",
    "net_amount": "12454.2800000"
}
```