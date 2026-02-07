import JSEncrypt from "jsencrypt";
import api from "./api";

export interface LoginResponse {
    token: {
        token: string;
        tokenName: string;
        timeout: number;
    };
    account: string;
    email: string;
}

export const getPublicKey = async (keyId: string): Promise<string> => {
    const response = await api.get(`/open/cas/public/${keyId}/key`);
    return response.data;
};

export const encryptText = (text: string, publicKey: string): string => {
    const encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    const encrypted = encrypt.encrypt(text);
    if (!encrypted) {
        throw new Error("Encryption failed");
    }
    return encrypted;
};

export const logout = async () => {
    try {
        await api.get("/open/cas/logout");
    } finally {
        localStorage.removeItem("pism_token");
        localStorage.removeItem("pism_token_name");
        localStorage.removeItem("pism_user");
        window.location.href = "/login";
    }
};
