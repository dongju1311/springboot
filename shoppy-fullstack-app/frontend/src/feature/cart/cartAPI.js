import React from 'react';
import { addCartItem, updateCartCount, 
         showCartItem, updateTotalPrice,
         updateCartItem, removeCartItem } from './cartSlice.js';
import { axiosData } from '../../utils/dataFetch.js';

export const removeCart = (cid) => async(dispatch) => {
    dispatch(removeCartItem({"cid": cid}));
    dispatch(updateTotalPrice());
    dispatch(updateCartCount()); 
}

export const updateCart = (cid, type) => async (dispatch) => {
    dispatch(updateCartItem({"cid":cid, "type":type})); //수량변경
    dispatch(updateTotalPrice());
    dispatch(updateCartCount());    
}


export const showCart = () => async (dispatch) => {
    const jsonData = await axiosData("/data/products.json");
    dispatch(showCartItem({"items": jsonData}));
    dispatch(updateTotalPrice());
}

export const addCart = (pid, size) => async (dispatch) => {
    dispatch(addCartItem({"cartItem":{"pid":pid, "size":size, "qty":1}})); 
    dispatch(updateCartCount());
}

