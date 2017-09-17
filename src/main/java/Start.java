import my_parser.Example;
import my_parser.Item;
import my_parser.dbUtil;
import my_parser.app;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Start {

    static List<Item> posts;
    static Item item;
    static int pagesM;
    static app app1;

    public static String[] eng={"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
    public static ArrayList<String> eng_words=new ArrayList<String>();
    public static HashMap<String,Integer> hashMap=new HashMap<String,Integer>();
    public static java.sql.Connection con;

    public static String[] stop_word={"and","or","to","too","a","the","of","in","with","at","<span","are","by","at","etc","that","but","for","as","an","on","you"};
    //final static String[] langs={"java","C#","pyton"};
    final static String[] langs={"java","C#","python","JavaScript","Ruby","PHP","C++","Go","Objective-C","Scala","Swift","kotlin"};

    public interface LoginCallbacks{
        void onLogin(int pages,String lang,ArrayList<String> engW,int pageCount);
        void onFinal(ArrayList<String> engW);
    }

    static private LoginCallbacks LoginCallbacks(){

        LoginCallbacks loginCallbacks = new LoginCallbacks() {
            public void onLogin(int pages,final String lang,final ArrayList<String> engW,int pageCount) {
                pagesM=pages;
                System.out.println("pagesM_"+pagesM+" for "+lang);
                for (int i = 1; i < (pagesM+1); i++) {
                    pageCount++;

                    getData(i,pageCount, new LoginCallbacks() {

                        public void onLogin(int pages,String lang, ArrayList<String> engW,int pageCount) {
                        }

                        public void onFinal(ArrayList<String> engW) {
                            //TimeUnit.
                            HashMap<String,Integer> hashMap_loc=new HashMap<String,Integer>();
                            //for(String elm:eng_words) {
                            for(String elm:engW) {
                                if (hashMap_loc.containsKey(elm)){
                                    int curr_v=hashMap_loc.get(elm);
                                    hashMap_loc.put(elm,curr_v+1);
                                }
                                else {
                                    hashMap_loc.put(elm,1);
                                }
                            }
                            System.out.println("do onFinal, size: "+hashMap_loc.size()+" for "+lang);
                            dbUtil.insertData(con,hashMap_loc,lang);
                            //eng_words.clear();
                            //hashMap.clear();
                        }
                    }, lang, engW);

                    //System.out.println("page___"+i); // это все равно выполнится раньше, чем вывод данных в getData
                }
            }
            public void onFinal( ArrayList<String> engW) {

            }

        };

        return loginCallbacks;
    }


    public static void main(String[] args) {

        app1=new app();
        app1.RetrofitCreation();


        final dbUtil dbUtil = new dbUtil();
        con = dbUtil.dbConnect();


        for (String lang:langs) {
            final LoginCallbacks loginCallbacks = LoginCallbacks();
            getData(0,0 ,loginCallbacks,lang,new ArrayList<String>());
        }



    }

    public static void getData(final int page,final int pageCount, final LoginCallbacks loginCallbacks,final String resourceName,final ArrayList<String> engW){

        app1.getApi().getData(resourceName,1000, 100, page).enqueue(new Callback<Example>() {
            public void onResponse(Call<Example> call, Response<Example> response) {

                if (response.body()!=null){
                    int pages = response.body().getPages();
                    if (page == 0){
                        //loginCallbacks.
                        loginCallbacks.onLogin(pages,resourceName,engW,pageCount);
                    }

                    read_response(response,engW);
                    System.out.println("page -> "+page+" its "+resourceName);
                    //if (page+1==pages){
                    if (pageCount+1==pages){
                        loginCallbacks.onFinal(engW);
                    }
                }


            }

            public void onFailure(Call<Example> call, Throwable throwable) {
                System.out.println("onFailure"+throwable.getMessage().toString());
            }
        });
    }

    public static void read_response(Response<Example> response,ArrayList<String> engW){

        List examples=response.body().getItems();
        int size=examples.size();
        for (int i = 1; i < size; i++) {
            Item item= (Item) examples.get(i);
            String req=item.getSnippet().getRequirement();
            String res =item.getSnippet().getResponsibility();

            SentCutter(req,engW);
            SentCutter(res,engW);

            //System.out.println("req"+req);
            //System.out.println("res"+res);
            //System.out.println("i"+i+"--------------------------------------------------");
        }

    }

    public static void SentCutter(String sent,ArrayList<String> engW){

        if (sent!=null){
            String[] words_m=sent.split(" ");
            for (String elm_word:words_m) {
            if (IsEngWord(elm_word)) {
                //eng_words.add(Clearing(elm_word));
                engW.add(Clearing(elm_word));
            }
            }
        }

    }

    public static boolean IsEngWord(String word){
        if(IsStopWord(word)){
            return false;
        }
        for (String eng_l : eng) {
            if (word.contains(eng_l)) {
                return true;
            }
        }
        return false;
    }

    public static String Clearing(String elm_word){
        String elm_word_1 = elm_word.replace(".","");
        String elm_word_2 = elm_word_1.replace(",","");
        String elm_word_3 = elm_word_2.replace(";","");
        return elm_word_3.toLowerCase();
    }

    public static boolean IsStopWord(String word){
        for (String stop:stop_word) {
            if (word.toLowerCase().equals(stop)){
                return true;
            }
        }
        return false;
    }

//    static void Show_hashMap(){
//
//        DefaultTableModel tableModel=new DefaultTableModel(){Class[] types = { String.class, Integer.class};
//            @Override
//            public Class<?> getColumnClass(int columnIndex) {
//                return this.types[columnIndex];
//            }
//        };
//        JTable jt = new JTable(tableModel);
//        jt.setAutoCreateRowSorter(true);
//        tableModel.addColumn("Technology");
//        tableModel.addColumn("Count");
//
//        Iterator it=hashMap.entrySet().iterator();
//        while (it.hasNext()){
//            Map.Entry pair=(Map.Entry)it.next();
//            tableModel.addRow(new Object[]{pair.getKey(),pair.getValue()});
//        }
//
//        TableRowSorter<TableModel> trs=new TableRowSorter<TableModel>(tableModel);
//        jt.setRowSorter(trs);
//        ArrayList <RowSorter.SortKey> SortKeys=new ArrayList<>(25);
//        SortKeys.add(new RowSorter.SortKey(1,SortOrder.DESCENDING));
//        trs.setSortKeys(SortKeys);
//
//        JOptionPane.showMessageDialog(null,new JScrollPane(jt));
//
//    }
}


