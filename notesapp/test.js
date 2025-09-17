import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10, 
    duration: '1m'
}

export default function() {
    const uniqueId = `user${__VU}_${__ITER}_${Date.now()}`;

    const user = newUser(uniqueId);
    sleep(1);

    let login = null;
    if (user) {
        login = loginUser(user, uniqueId);
    }
    sleep(1);

    if (login && login.token) {
        newNote(login.token);
    }
    sleep(1);
}

function newUser(uniqueId)
{
    const url = 'http://localhost:8080/api/auth/register';
    const payload = JSON.stringify({
        "username": uniqueId,
        "email": `${uniqueId}@gmail.com`,
        "password" : uniqueId
    });
    const params = {
        headers: {
            'Content-type': 'application/json'
        }
    }

    const res = http.post(url, payload, params);
    check(res, {
            'status is 200 or 201': (r) => r.status === 200 || r.status === 201,
            'response is not empty': (r) => r.body.length > 0,
    }); 

    let user;
    try
    {
        user = JSON.parse(res.body);
    }
    catch (e)
    {
        user = null;
    }

    return user;
}


function loginUser(user, uniqueId)
{
    const url = 'http://localhost:8080/api/auth/login';
    const payload = JSON.stringify({
        "usernameOrEmail": uniqueId,
        "password" : uniqueId
    });

    const params = {
        headers: {
            'Content-type': 'application/json'
        }
    }

    const res = http.post(url, payload, params);

    check(res, {
            'status is 200 or 201': (r) => r.status === 200 || r.status === 201,
            'response is not empty': (r) => r.body.length > 0,
        });

    let login;
    try
    {
        login = JSON.parse(res.body);
    }
    catch (e)
    {
        login = null;
        console.log("Error", e);
    }

    return login;
}

function newNote(token) {
    const url = "http://localhost:8080/api/notes";
    const payload = JSON.stringify({
        "title": `note${__ITER}`,
        "content": `content note${__ITER}`,
        "tagIds" : [7]
    });
    
    const params = {
        headers: {
            'Content-type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    }
    const res = http.post(url, payload, params);
    check(res, {
            'status is 200 or 201': (r) => r.status === 200 || r.status === 201,
            'response is not empty': (r) => r.body.length > 0,
    });
}

// tomcat.threads.current
// tomcat.threads.busy
// tomcat.threads.config.max