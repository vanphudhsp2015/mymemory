package com.example.vanphu.mymoney.Controller;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vanphu.mymoney.Adapter.SpendAdapter;
import com.example.vanphu.mymoney.Model.SpendModel;
import com.example.vanphu.mymoney.R;
import com.example.vanphu.mymoney.SpendActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class SpendController {
    private ArrayList<SpendModel> mArrayList;
    private SpendAdapter mAdapter;
    private ArrayList<String> mArrayImage;
    private ArrayList<String> mArrayImageAvatar;
    private Context mContext;
    private int mIdImage;
    private int mMoneyIn = 0;
    public static String sKeyMoney = "";
    private int mMoneySum = 0;
    private int mMoneyOut = 0;
    private ArrayList<String> mArraymonth;

    public SpendController(Context Context) {
        mContext = Context;
        this.mArrayList = new ArrayList<>();
        mAdapter = new SpendAdapter(mContext, mArrayList);
        String[] arrayAvatar = mContext.getResources().getStringArray(R.array.list_image_spend);
        mArrayImage = new ArrayList<>(Arrays.asList(arrayAvatar));
    }

    public void addImage(ListView lv_Spend) {
        mArrayList.add(new SpendModel(1, R.drawable.img_spend_item24, "Chi TIeu 1", 1000, "avc", "sadsd"));
        lv_Spend.setAdapter(mAdapter);
    }

    public void readJsonSpend(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                final int mIdImage = mContext.getResources().getIdentifier(mArrayImage.get(object.getInt("idhinh")), "drawable", mContext.getPackageName());
                                mArrayList.add(new SpendModel(object.getInt("id"), mIdImage, object.getString("tenchitieu"), object.getInt("giachitieu"),
                                        object.getString("email"), object.getString("ngay")
                                ));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void readJsonNav(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                SpendActivity.sTxtName.setText(object.getString("name"));
                                SpendActivity.sTxtUser.setText(object.getString("email"));
                                mIdImage = object.getInt("idhinh");
                                String[] mangten = mContext.getResources().getStringArray(R.array.list_image_avatar);
                                mArrayImageAvatar = new ArrayList<>(Arrays.asList(mangten));
                                final int idImage = mContext.getResources().getIdentifier(mArrayImageAvatar.get(mIdImage), "drawable", mContext.getPackageName());
                                SpendActivity.sImg_Avatar.setImageResource(idImage);
                            } catch (Exception e) {
                                Toast.makeText(mContext, "Lỗi" + e, Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Lỗi 123" + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void readJsonMoney(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                DecimalFormat formatter = new DecimalFormat("###,###,###");
                                mMoneyIn = object.getInt("tienvao");
                                sKeyMoney = object.getString("matien");
                                String sumMoney = formatter.format(mMoneyIn) + " " + sKeyMoney;
                                SpendActivity.txt_MoneyIn.setText(sumMoney);
                                mMoneySum = object.getInt("tienvao");

                            } catch (Exception e) {
                                Toast.makeText(mContext, "Lỗi" + e, Toast.LENGTH_LONG).show();
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Lỗi 123" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void ReadJsonDate(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        svlist.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                SpendActivity.sTxt_Day.setText(object.getString("ngay"));
                                int numberDate = object.getInt("ngay");
                                int numberMonth = object.getInt("thang");
                                int numberYear = object.getInt("nam");
                                if (numberMonth < 3) {
                                    numberMonth = numberMonth + 12;
                                    numberYear = numberYear - 1;
                                }
                                int n = (numberDate + 2 * numberMonth + (3 * (numberMonth + 1)) / 5 + numberYear + (numberYear / 4)) % 7;
                                String DATE = "";
                                if (n == 0) {
                                    DATE = "Chủ Nhật";
                                } else if (n == 1) {
                                    DATE = "Thứ Hai";
                                } else if (n == 2) {
                                    DATE = "Thứ Ba";
                                } else if (n == 3) {
                                    DATE = "Thứ Tư";
                                } else if (n == 4) {
                                    DATE = "Thứ Năm";
                                } else if (n == 5) {
                                    DATE = "Thứ Sáu";
                                } else if (n == 6) {
                                    DATE = "Thứ Bảy";
                                }
                                SpendActivity.sTxt_Date.setText(DATE);
                                String[] mangten = mContext.getResources().getStringArray(R.array.thang);
                                mArraymonth = new ArrayList<>(Arrays.asList(mangten));

                                SpendActivity.sTxt_Month.setText(mArraymonth.get(object.getInt("thang")));

                                SpendActivity.sTxt_Year.setText(String.valueOf(object.getInt("nam")));
                            } catch (Exception e) {
                                Toast.makeText(mContext, "Lỗi123" + e, Toast.LENGTH_LONG).show();
                            }
                        }
//                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Lỗi 123" + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }

        );
        requestQueue.add(jsonArrayRequest);
    }

    public void readJsonSum(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject object = response.getJSONObject(i);
                                mMoneyOut = object.getInt("sum1");
                                DecimalFormat formatter = new DecimalFormat("###,###,###");
                                String summoney_item = formatter.format(mMoneyOut) + " " + sKeyMoney;
                                SpendActivity.sTxt_SumSpendMoney.setText(summoney_item);
                                SpendActivity.sTxt_MoneyOut.setText(summoney_item);
                                mMoneySum = mMoneyIn - mMoneyOut;
                                String sum_main = formatter.format(mMoneySum) + " " + sKeyMoney;
                                SpendActivity.sTxt_MoneySum.setText(sum_main);
                            } catch (Exception e) {
                                Toast.makeText(mContext, "Lỗi" + e, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Lỗi 123" + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
