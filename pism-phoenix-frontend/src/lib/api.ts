import axios from "axios";
import { SUCCESS_FLAG } from "./types";
import type { JsonResult } from "./types";

const api = axios.create({
    baseURL: "http://127.0.0.1:8080",
    timeout: 10000,
    headers: {
        "Content-Type": "application/json",
    },
});

// Add a request interceptor to include the token in headers
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("pism_token");
        if (token) {
            const tokenName = localStorage.getItem("pism_token_name") || "pism-token";
            config.headers[tokenName] = token;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Add a response interceptor to handle authentication and JsonResult wrapper
api.interceptors.response.use(
    (response) => {
        const result = response.data as JsonResult<any>;

        // Check if the response is actually a JsonResult
        if (result && typeof result.code !== 'undefined') {
            if (result.code === SUCCESS_FLAG) {
                // Return the inner data
                return { ...response, data: result.data };
            } else {
                // Handle failed business logic
                return Promise.reject({
                    ...response,
                    data: result,
                    message: result.msg || "Operation failed"
                });
            }
        }

        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem("pism_token");
            localStorage.removeItem("pism_token_name");
            localStorage.removeItem("pism_user");
            window.location.href = "/login";
        }
        return Promise.reject(error);
    }
);

export default api;
