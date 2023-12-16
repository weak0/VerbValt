import {jwtDecode} from "jwt-decode";

const testToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJTVFVERU5UIl0sInN1YiI6Im1hY2lla0BnbWFpbC5jb20iLCJqdGkiOiI4IiwiaWF0IjoxNzAyNzMyNzI5LCJleHAiOjE3MDI4MTkxMjl9.1O831teF0dlOnYFefmFIQQEetlF7LLH6TMoUVG-AETc"

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
