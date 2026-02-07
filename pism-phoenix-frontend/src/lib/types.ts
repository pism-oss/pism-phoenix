export interface JsonResult<T> {
    code: number;
    msg: string;
    data: T;
    ts: number;
    ds: number;
    success: boolean;
    tid: string;
}

export const SUCCESS_FLAG = 1;
export const FAILED_FLAG = 0;
