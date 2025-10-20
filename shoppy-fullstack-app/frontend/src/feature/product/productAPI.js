import React from 'react';
import { createProduct, filterProduct } from './productSlice.js';
import {axiosData, groupByRows, axiosGet, axiosPost} from '../../utils/dataFetch.js';

export const getProduct = (pid) => async(dispatch) => {
    // dispatch(filterProduct(pid));
    const url = "/product/pid";
    const product = await axiosPost(url,{"pid":pid});
    console.log(product);
    dispatch(filterProduct({"product": product}));
}

export const getProductList = (number) => async(dispatch) => {
    // const jsonData = await axiosData("/data/products.json");
    const url="/product/all";
    const jsonData = await axiosGet(url);

    const rows = groupByRows(jsonData, number);
    dispatch(createProduct({"productList": rows, "products":jsonData}));
}

