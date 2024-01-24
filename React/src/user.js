import {jwtDecode} from "jwt-decode";


const userDecode = () => {
    const token = localStorage.getItem("token");
    if (token) {
        return {
            token: token,
            id: jwtDecode(token).jti,
        }
    }
}
export const data = {
    user:  userDecode()
}

export const fetchData = async (url, method, body = null, auth = true ) => {
    const options = {
        method: method,
    };

    if (method !== "GET" || method !=="DELETE" && body) {
        options.body = JSON.stringify(body);
    }
    if (auth){
        const currentUser = data.user;
        options.headers = {
            'Authorization': `Bearer ${currentUser ? currentUser.token : ''}`,
            'Content-Type': 'application/json'
        }
    }

    const response = await fetch(`http://127.0.0.1:8000/${url}`, options);

    if (response.ok) {
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.indexOf("application/json") !== -1) {
            return response.json();
        } else {
            console.log("Oops, we haven't got JSON!");
        }
    } else {
        alert("Error");
    }
}