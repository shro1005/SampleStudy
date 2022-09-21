

<API명세> 
- url : https://app.swaggerhub.com/apis/SHRO1004/Sample/1.0.0#/default/get_blog_topword
(server url : http://localhost:8444 로 설정후 테스트 진행필요)

servers:
  - description: Sample Blog Search API
  - url: http://localhost:8444
  
paths:
  /blog/search:
    get:
    
      description: search blog for search word
      
      parameters:
      
        - in: query
        
          name: query
          description: search word
          schema:
            type: string
            example: "test"
        - in: query
          name: sort
          description: sorting rule (accuracy or recency) (default is accuracy)
          schema:
            type: string
            default: "accuracy"
            example: "accuracy"
        - in: query
          name: page
          description: for pagination (default is 1 / max is 50)
          schema:
            type: integer
            default: 1
            example: 2
        - in: query
          name: size
          description: size of a page (default is 10 / max is 50)
          schema:
            type: integer
            default: 10
            example: 20   
      responses:
        '200':
          description: search response of blog request
          content:
            application/json:
              schema:
                type: object
                properties:
                  end_yn:
                    type: boolean
                    example : false
                  page:
                    type: integer 
                    example : 2
                  total_cnt : 
                    type: integer 
                    example : 2000
                  size :
                    type: integer 
                    example : 20
                  dataList:
                    type: array
                    items:
                      type: object
                      properties:
                        blogname:
                          type: string
                          example: 'this is my blog'
                        contents:
                          type: string
                          example: 'hi this is contents'
                        datatime:
                          type: string
                          description: 'yyyy-MM-dd'
                          example: '2022-09-09'
                        thumbnail:
                          type: string
                          example: 'https://thumbnail.thumb/1'
                        title:
                          type: string
                          example: 'i hope to go kakao bank'
                        url:
                          type: string
                          example: 'https://thisisblog.com/asdfasdf'
        '400':
          description: BadRequestError
          content:
            application/json:
              schema:
                type: object
                properties:
                  status:
                    type: integer
                    description: status code
                    example : 400
                  message:
                    type: string
                    example: 'page 값이 50을 초과했습니다.'
                  errorType:
                    type: string
                    example: 'InvalidArgument'
        '401':
          description: UNAUTHORIZED
          content:
            application/json:
              schema:
                type: object
                properties:
                  status:
                    type: integer
                    description: status code
                    example : 401
                  message:
                    type: string
                    example: '권한이 없습니다.'
                  errorType:
                    type: string
                    example: 'AccessDeniedError'    
        '500':
          description: InternalServerError
          content:
            application/json:
              schema:
                type: object
                properties:
                  status:
                    type: integer
                    description: status code
                    example : 500
                  message:
                    type: string
                    example: '서버에 문제가 발생하였습니다. 잠시후 다시 시도해주세요. 문제가 지속될 시 문의해주세요.'
                  errorType:
                    type: string
                    example: 'InternalServerError'               
  /blog/topword:
    get:
      description: search top 10 search word
      responses:
        '200':
          description: search response of blog request
          content:
            application/json:
              schema:
                type: object
                properties:
                  dataList:
                    type: array
                    items:
                      type: object
                      properties:
                        searchWord:
                          type: string
                          example: 'kakaobank'
                        searchCnt:
                          type: number
                          example: 2000
                        rank:
                          type: integer
                          example: 1
                    example: 
                      - {searchWord: 'kakaobank', searchCnt: 10000, rank: 1}
                      - {searchWord: 'kakaobank1', searchCnt: 1000, rank: 2}
                      - {searchWord: 'kakaobank2', searchCnt: 100, rank: 3}
                      - {searchWord: 'kakaobank3', searchCnt: 10, rank: 4}
                      - {searchWord: 'kakaobank4', searchCnt: 1, rank: 5}
                      - {searchWord: 'kakaobank5', searchCnt: 1, rank: 6}
                      - {searchWord: 'kakaobank6', searchCnt: 1, rank: 7}
                        
        '400':
          description: BadRequestError
          content:
            application/json:
              schema:
                type: object
                properties:
                  status:
                    type: integer
                    description: status code
                    example : 400
                  message:
                    type: string
                    example: 'page 값이 50을 초과했습니다.'
                  errorType:
                    type: string
                    example: 'InvalidArgument'
        '401':
          description: UNAUTHORIZED
          content:
            application/json:
              schema:
                type: object
                properties:
                  status:
                    type: integer
                    description: status code
                    example : 401
                  message:
                    type: string
                    example: '권한이 없습니다.'
                  errorType:
                    type: string
                    example: 'AccessDeniedError'    
        '500':
          description: InternalServerError
          content:
            application/json:
              schema:
                type: object
                properties:
                  status:
                    type: integer
                    description: status code
                    example : 500
                  message:
                    type: string
                    example: '서버에 문제가 발생하였습니다. 잠시후 다시 시도해주세요. 문제가 지속될 시 문의해주세요.'
                  errorType:
                    type: string
                    example: 'InternalServerError'

