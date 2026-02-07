import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Lock, User, ShieldCheck } from "lucide-react";
import { nanoid } from "nanoid";
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
            const keyId = nanoid();

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
            <Card className="w-full max-w-[500px] shadow-xl border-slate-200 overflow-hidden rounded-[40px]">
                <CardHeader className="space-y-2 flex flex-col items-center pt-10 pb-6">
                    <div className="p-4 bg-primary/10 rounded-[24px] mb-4 shadow-inner ring-1 ring-primary/20">
                        <ShieldCheck className="w-12 h-12 text-primary" />
                    </div>
                    <CardTitle className="text-4xl font-black tracking-tighter text-slate-900 leading-none">Pism Phoenix</CardTitle>
                    <CardDescription className="text-slate-400 text-xl font-medium tracking-tight">
                        后台管理系统
                    </CardDescription>
                </CardHeader>
                <CardContent className="px-10 pb-10">
                    <form onSubmit={handleLogin} className="flex flex-col gap-8">
                        <div className="flex flex-col gap-5">
                            <div className="relative group">
                                <User className="absolute left-4 top-1/2 -translate-y-1/2 h-6 w-6 text-slate-400 transition-colors group-focus-within:text-primary" />
                                <Input
                                    type="text"
                                    placeholder="账号"
                                    className="pl-12 h-16 text-xl rounded-[20px] bg-slate-50 border-none shadow-inner focus-visible:ring-2 focus-visible:ring-primary/20 transition-all font-medium"
                                    value={account}
                                    onChange={(e) => setAccount(e.target.value)}
                                    disabled={isLoading}
                                    autoComplete="username"
                                />
                            </div>
                            <div className="relative group">
                                <Lock className="absolute left-4 top-1/2 -translate-y-1/2 h-6 w-6 text-slate-400 transition-colors group-focus-within:text-primary" />
                                <Input
                                    type="password"
                                    placeholder="密码"
                                    className="pl-12 h-16 text-xl rounded-[20px] bg-slate-50 border-none shadow-inner focus-visible:ring-2 focus-visible:ring-primary/20 transition-all font-medium"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    disabled={isLoading}
                                    autoComplete="current-password"
                                />
                            </div>
                        </div>
                        <Button
                            type="submit"
                            className="w-full h-16 text-2xl font-black rounded-[24px] shadow-lg shadow-primary/20 hover:scale-[1.02] active:scale-[0.98] transition-all"
                            disabled={isLoading}
                        >
                            {isLoading ? "登录中..." : "登录"}
                        </Button>
                    </form>
                </CardContent>
                <CardFooter className="flex flex-col space-y-4 py-8 bg-slate-50/50">
                    <div className="text-xs text-slate-300 font-bold tracking-widest uppercase">
                        Pism Phoenix Management Platform © 2026
                    </div>
                </CardFooter>
            </Card>
        </div>
    );
}
