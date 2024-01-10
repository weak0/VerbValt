import {jwtDecode} from "jwt-decode";

const decodeToken = (token) => {
    const decoded = jwtDecode(token);
    return decoded;
}

export const user  = {
    token: localStorage.getItem("token"),
    id: decodeToken(token).jti,
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
