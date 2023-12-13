import {jwtDecode} from "jwt-decode";

const testToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWNpZWtAZXhhbXBsZS54ZCIsImp0aSI6IjExIiwiaWF0IjoxNzAyNDI3MDE1LCJleHAiOjE3MDI1MTM0MTV9.VQnnvMws4oQ5dzemB9kDC2-CdQC3eOX6adRQ-5bzxac"

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
