import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Lock, User, ShieldCheck } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import api from "@/lib/api";
import { getPublicKey, encryptText } from "@/lib/auth";
import type { LoginResponse } from "@/lib/auth";

export default function Login() {
    const [account, setAccount] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    const { toast } = useToast();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!account || !password) {
            toast({
                variant: "destructive",
                title: "错误",
                description: "请输入账号和密码。",
            });
            return;
        }

        setIsLoading(true);
        try {
            // 为本次登录请求生成唯一的 keyId
            const keyId = crypto.randomUUID();

            const publicKey = await getPublicKey(keyId);

            const encryptedAccount = encryptText(account, publicKey);
            const encryptedPassword = encryptText(password, publicKey);

            await api.post<LoginResponse>("/open/cas/login", {
                account: encryptedAccount,
                password: encryptedPassword,
                keyId: keyId,
            }).then((response) => {
                const { token, account: userAccount, email } = response.data;

                localStorage.setItem("pism_token", token.token);
                localStorage.setItem("pism_token_name", token.tokenName);
                localStorage.setItem("pism_user", JSON.stringify({ account: userAccount, email }));

                toast({
                    title: "成功",
                    description: "登录成功。",
                });

                navigate("/");
            });

        } catch (error: any) {
            console.error("Login failed:", error);
            toast({
                variant: "destructive",
                title: "登录失败",
                description: error.message || error.response?.data?.msg || "账号或密码错误。",
            });
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="min-h-screen w-full flex items-center justify-center bg-slate-50 p-4">
            <Card className="w-full max-w-[500px] shadow-xl border-slate-200">
                <CardHeader className="space-y-1 flex flex-col items-center">
                    <div className="p-3 bg-primary/10 rounded-full mb-2">
                        <ShieldCheck className="w-10 h-10 text-primary" />
                    </div>
                    <CardTitle className="text-3xl font-bold tracking-tight">Pism Phoenix</CardTitle>
                    <CardDescription className="text-slate-500 text-lg">
                        后台管理系统
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <form onSubmit={handleLogin} className="space-y-6">
                        <div className="space-y-3">
                            <div className="relative">
                                <User className="absolute left-3 top-3 h-5 w-5 text-slate-400" />
                                <Input
                                    type="text"
                                    placeholder="账号"
                                    className="pl-10 h-12 text-lg"
                                    value={account}
                                    onChange={(e) => setAccount(e.target.value)}
                                    disabled={isLoading}
                                />
                            </div>
                            <div className="relative">
                                <Lock className="absolute left-3 top-3 h-5 w-5 text-slate-400" />
                                <Input
                                    type="password"
                                    placeholder="密码"
                                    className="pl-10 h-12 text-lg"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    disabled={isLoading}
                                />
                            </div>
                        </div>
                        <Button
                            type="submit"
                            className="w-full h-12 text-lg font-semibold transition-all hover:scale-[1.01]"
                            disabled={isLoading}
                        >
                            {isLoading ? "登录中..." : "登录"}
                        </Button>
                    </form>
                </CardContent>
                <CardFooter className="flex flex-col space-y-4 pt-0">
                    <div className="text-sm text-slate-400 text-center">
                        Pism Phoenix 管理平台 © 2026
                    </div>
                </CardFooter>
            </Card>
        </div>
    );
}
