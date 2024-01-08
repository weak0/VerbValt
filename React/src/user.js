import {jwtDecode} from "jwt-decode";

const testToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJTVFVERU5UIl0sInN1YiI6InN0cmluZyIsImp0aSI6IjEyIiwiaWF0IjoxNzA0NzM4OTMwLCJleHAiOjE3MDQ4MjUzMzB9.9M47YEZ-EEHffMQUpS6ZZ_LISYb2l0Kgta9XV2HDNVI"

const decodeToken = (token) => {
    const decoded = jwtDecode(token);
    console.log(decoded)
    return decoded;
}

export const testuser = {
    token: testToken,
    id: decodeToken(testToken).jti,
}

export const fetchData = async (url, method, body = null) => {
    const options = {
        method: method,
        headers: {
            'Authorization': `Bearer ${testuser.token}`,
            'Content-Type': 'application/json'
        },
    };

    if (method !== "GET" && body) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(`http://127.0.0.1:8000/${url}`, options);

    if (response.ok) {
        return response.json();
    } else {
        alert("Error");
    }
}
