# API

## Like 추천

### 조회: [GET] /api/likes
#### Parameters
- sort: ex) sort=createdAt,desc 
- name: 이름 검색

### 추천 취소: [DELETE] /api/likes/{id}
#### Parameters
- {id}: like 식별자

-----

## Work 제출작

### 조회: [GET] /api/works
#### Parameters
- sort: ex)sort=createdAt,desc
- author: ex)author=kim
#### ResponseBody
```js
[{
    id: 1,
    article: "article",
    author: "author",
    user: {
        id: 1
        email: "email",
        name: "name",
        password: "password"
    }
}]
```


### 추가: [POST] /api/works
#### Request Body
```js
{
    article: "article",
    author: "author",
    user: {
        email: "email",
        name: "name",
        password: "password"
    }
}
```
#### ResponseBody
```js
{
    id: 1,
    article: "article",
    author: "author",
    user: {
        id: 1
        email: "email",
        name: "name",
        password: "password"
    }
}
```

-----

## User 유저

### 조회: [GET] /api/user/me
#### ResponseBody
```js
{
    id: 1,
    email: "email",
    name: "name",
    password: "password",
    createdAt: 1577611361,
    updatedAt: 1577611361,
    role: {
        id: 1,
        name: "user"
    }
}
```
