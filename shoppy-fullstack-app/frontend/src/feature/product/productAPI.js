import React from 'react';
import { createProduct, filterProduct } from './productSlice.js';
import {axiosData, groupByRows, axiosGet, axiosPost} from '../../utils/dataFetch.js';


export const getProduct = (pid) => async(dispatch) => {
    // dispatch(filterProduct(pid));
    const url = "/product/pid";
    const product = await axiosPost(url,{"pid":pid});
    // console.log(product);
    dispatch(filterProduct({"product": product}));
}

export const getProductList = (number) => async(dispatch) => {
    // const jsonData = await axiosData("/data/products.json");
    const url="/product/all";
    const jsonData = await axiosGet(url);

    const rows = groupByRows(jsonData, number);
    dispatch(createProduct({"productList": rows, "products":jsonData}));
}
/**
 * 상품 상세 정보 - 서버 연동
 */
export const getDetailinfo = async (pid) => {
    const url = "/product/detailinfo";
    const detailinfo = await axiosPost(url, {"pid": pid});
    const list = JSON.parse(detailinfo.list);
    // console.log(detailinfo);
    return {...detailinfo, list:list};
}
/**
 * 상품 qna
 */
export const getQna = async (pid) => {
    const url = "/product/qna";
    const qna = await axiosPost(url,{"pid":pid});
    return qna;
}
/**
 * 상품 Return
 */
export const getReturn = async () => {
    const url = "/product/return";
    const returnData = await axiosGet(url);
    const list = JSON.parse(returnData.list);
    return {...returnData, list: list};
}

