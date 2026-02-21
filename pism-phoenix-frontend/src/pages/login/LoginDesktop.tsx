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

export default function LoginDesktop() {
    const [account, setAccount] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    const { toast } = useToast();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!account || !password) {
            toast({ variant: "destructive", title: "错误", description: "请输入账号和密码。" });
            return;
        }

        setIsLoading(true);
        try {
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
                toast({ title: "成功", description: "登录成功。" });
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
        <div className="min-h-screen w-full flex items-center justify-center bg-slate-50 p-8">
            <Card className="w-full max-w-[520px] shadow-2xl border-none overflow-hidden rounded-[40px] bg-white">
                <CardHeader className="space-y-4 flex flex-col items-center pt-16 pb-12 px-12">
                </CardHeader>
                <CardContent className="px-16 pb-16">
                    <form onSubmit={handleLogin} className="flex flex-col gap-12">
                        <div className="flex flex-col gap-8">
                            <div className="relative group">
                                <User className="absolute left-6 top-1/2 -translate-y-1/2 h-8 w-8 text-slate-300 transition-colors group-focus-within:text-primary" />
                                <Input
                                    type="text"
                                    placeholder="账号名称"
                                    className="pl-16 h-20 text-2xl rounded-[28px] bg-slate-50 border-none shadow-inner focus-visible:ring-2 focus-visible:ring-primary/20 transition-all font-bold placeholder:text-slate-300 placeholder:font-medium"
                                    value={account}
                                    onChange={(e) => setAccount(e.target.value)}
                                    disabled={isLoading}
                                    autoComplete="username"
                                />
                            </div>
                            <div className="relative group">
                                <Lock className="absolute left-6 top-1/2 -translate-y-1/2 h-8 w-8 text-slate-300 transition-colors group-focus-within:text-primary" />
                                <Input
                                    type="password"
                                    placeholder="登录密码"
                                    className="pl-16 h-20 text-2xl rounded-[28px] bg-slate-50 border-none shadow-inner focus-visible:ring-2 focus-visible:ring-primary/20 transition-all font-bold placeholder:text-slate-300 placeholder:font-medium"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    disabled={isLoading}
                                    autoComplete="current-password"
                                />
                            </div>
                        </div>
                        <Button
                            type="submit"
                            className="w-full h-20 text-3xl font-black rounded-[32px] shadow-[0_20px_40px_-15px_rgba(var(--primary-rgb),0.3)] hover:scale-[1.02] active:scale-[0.98] transition-all bg-primary hover:bg-primary/90"
                            disabled={isLoading}
                        >
                            {isLoading ? (
                                <div className="flex items-center gap-3">
                                    <div className="w-8 h-8 border-4 border-white/30 border-t-white rounded-full animate-spin" />
                                    <span>验证中</span>
                                </div>
                            ) : "登录"}
                        </Button>
                    </form>
                </CardContent>
            </Card>
        </div>
    );
}
