import {jwtDecode} from "jwt-decode";

const testToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWNpZWtAZXhhbXBsZS54ZCIsImp0aSI6IjExIiwiaWF0IjoxNzAyNDEyOTIxLCJleHAiOjE3MDI0OTkzMjF9.KoAEChD_nG_7rnwF66mapWlUkl7ONvT9YSsS9FjYI6c"

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
