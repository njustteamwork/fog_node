package com.njust.fog_node.test;

import com.google.gson.Gson;
import com.njust.fog_node.paillier.PaillierPublicKey;

public class PaillierTest {
    public static void main(String args[]){
    PaillierPublicKey paillierPublicKey = PaillierPublicKey.paillierJsonToPublicKey("{\"n\":7037996759611275900405487329144489085210900622405788623915340046554895678557675360099993502545810105916795350348201798995744651664108236879690390748857833,\"nSquare\":49533398388298819693190911443085500113137594389227717398938303574532356291531019850234314622241175041250992063305927006862844026670633749958420794136365527887009273250901790502746504678689585917463571409706569379921923499464969602901871572009667889989146252127852333968575165007138552703354893437794045455889,\"g\":47,\"bitLength\":512,\"timeStamp\":1580452220178}");
    Gson gson = new Gson();
    String s = gson.toJson(paillierPublicKey);
    System.out.println(s);
    PaillierPublicKey paillierPublicKey1 = null;
    paillierPublicKey.saveToFile();
    paillierPublicKey1 = PaillierPublicKey.readFromFile();
    s = gson.toJson(paillierPublicKey1);
    System.out.println(s);
    }

}
