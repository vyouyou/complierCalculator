package com.youyou.conf;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vyouyou on 17-9-10.
 */
public class ExpressBase {
    List<String> symbolQuene = new ArrayList<String>();
    List<BaseData> objList;
    int curIndex = 0;
    int objListLength = 0;

    public ExpressBase(List<BaseData> objList) {
        this.objList = objList;
        this.objListLength = objList.size();
    }


    public void startExpress() {
        try {
            ExpressInner inner = new ExpressInner();
            getExtend(SymbolEnums.A, objList.get(0).key, inner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void removeHead() {
        if (this.symbolQuene.size() > 0) {
            this.symbolQuene.remove(0);
        }
    }

    private void getExtend(SymbolEnums symbol, String first, ExpressInner node) throws Exception {
        node.leftNode = new ExpressInner();
        node.rightNode = new ExpressInner();
        if (symbol.equals(SymbolEnums.A)) {
            if (first.equals("number") || first.equals("(")) {
                removeHead();
                symbolQuene.add(0, SymbolEnums.B.getSymbol());
                symbolQuene.add(1, SymbolEnums.C.getSymbol());
                if (node.leftNode.syn == null) {
                    getExtend(SymbolEnums.B, first, node.leftNode);
                }
                node.rightNode.inh = node.leftNode.syn;
                if (node.rightNode.syn == null) {
                    getExtend(SymbolEnums.C, objList.get(curIndex).key, node.rightNode);
                }
                node.syn = node.rightNode.syn;
            }
        } else if (symbol.equals(SymbolEnums.B)) {
            if (first.equals("number") || first.equals("(")) {
                removeHead();
                symbolQuene.add(0, SymbolEnums.D.getSymbol());
                symbolQuene.add(1, SymbolEnums.E.getSymbol());
                if (node.leftNode.syn == null) {
                    getExtend(SymbolEnums.D, first, node.leftNode);
                }
                node.rightNode.inh = node.leftNode.syn;
                if (node.rightNode.syn == null) {
                    getExtend(SymbolEnums.E, objList.get(curIndex).key, node.rightNode);
                }
                node.syn = node.rightNode.syn;
                System.out.println("b" + node.syn + "e" + node.rightNode.syn);
            }
        } else if (symbol.equals(SymbolEnums.C)) {
            if (first.equals("+") || first.equals("-")) {
                removeHead();
                symbolQuene.add(0, first);
                symbolQuene.add(1, SymbolEnums.B.getSymbol());
                symbolQuene.add(2, SymbolEnums.C.getSymbol());
                this.reduceQuene();
                if (node.leftNode.syn == null) {
                    getExtend(SymbolEnums.B, this.objList.get(curIndex).key, node.leftNode);
                }
                if (first.equals("+")) {
                    node.rightNode.inh = node.inh + node.leftNode.syn;
                } else if (first.equals("-")) {
                    node.rightNode.inh = node.inh - node.leftNode.syn;
                }
                if (node.rightNode.syn == null) {
                    getExtend(SymbolEnums.C, this.objList.get(curIndex).key, node.rightNode);
                }
                node.syn = node.rightNode.syn;
            } else if (first.equals(")")) {
                node.syn = node.inh;
                this.reduceQuene();
            } else if (first.equals("$")) {
                removeHead();
                node.syn = node.inh;
            }
        } else if (symbol.equals(SymbolEnums.D)) {
            if (first.equals("number")) {
                removeHead();
                symbolQuene.add(0, "number");
                node.syn = Float.valueOf(this.objList.get(curIndex).value);
                reduceQuene();
            } else if (first.equals("(")) {
                removeHead();
                symbolQuene.add(0, "(");
                symbolQuene.add(1, SymbolEnums.A.getSymbol());
                symbolQuene.add(2, ")");
                reduceQuene();
                if (node.leftNode.syn == null) {
                    getExtend(SymbolEnums.A, this.objList.get(curIndex).key, node.leftNode);
                }
                node.syn = node.leftNode.syn;
            }
        } else if (symbol.equals(SymbolEnums.E)) {
            if (first.equals("*") || first.equals("/")) {
                removeHead();
                symbolQuene.add(0, first);
                symbolQuene.add(1, SymbolEnums.D.getSymbol());
                symbolQuene.add(2, SymbolEnums.E.getSymbol());
                this.reduceQuene();
                if (node.leftNode.syn == null) {
                    getExtend(SymbolEnums.D, this.objList.get(curIndex).key, node.leftNode);
                }
                if (first.equals("*")) {
                    System.out.println("e" + node.inh + node.leftNode.syn);
                    node.rightNode.inh = node.inh * node.leftNode.syn;
                } else if (first.equals("/")) {
                    node.rightNode.inh = node.inh / node.leftNode.syn;
                }
                if (node.rightNode.syn == null) {
                    System.out.println("e" + this.objList.get(curIndex).key);
                    getExtend(SymbolEnums.E, this.objList.get(curIndex).key, node.rightNode);
                    System.out.println("e.syn" + node.rightNode.syn);
                }
                node.syn = node.rightNode.syn;
            } else if (first.equals(")")) {
                reduceQuene();
            } else if (first.equals("$") || first.equals("+") || first.equals("-")) {
                removeHead();
                node.syn = node.inh;
            }
        } else {
            throw new Exception("error quene");
        }
    }

    /**
     * 终结符号比较删除
     */
    private void reduceQuene() {
        if (symbolQuene.get(0).equals(this.objList.get(curIndex).key)) {
            symbolQuene.remove(0);
            curIndex++;
        }
    }

    public class ExpressInner {
        ExpressInner leftNode, rightNode;
        Float inh = null, syn = null;
        SymbolEnums type;

        public ExpressInner() {

        }
    }
}
