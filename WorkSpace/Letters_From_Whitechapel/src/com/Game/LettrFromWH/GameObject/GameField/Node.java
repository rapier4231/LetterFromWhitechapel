package com.Game.LettrFromWH.GameObject.GameField;

public class Node extends FieldPiece{

    public Node(String name, String nodeType, String canMoveTop, String canMoveRightTop,
                String canMoveRight, String canMoveRightBottom, String canMoveBottom,
                String canMoveLeftBottom, String canMoveLeft, String canMoveLeftTop ){
        this.name = name;
        this.nodeType = changeStringToNodeType(nodeType);
        this.canMoveTop = changeStringToBoolean(canMoveTop);
        this.canMoveRightTop = changeStringToBoolean(canMoveRightTop);
        this.canMoveRight= changeStringToBoolean(canMoveRight);
        this.canMoveRightBottom= changeStringToBoolean(canMoveRightBottom);
        this.canMoveBottom= changeStringToBoolean(canMoveBottom);
        this.canMoveLeftBottom= changeStringToBoolean(canMoveLeftBottom);
        this.canMoveLeft= changeStringToBoolean(canMoveLeft);
        this.canMoveLeftTop= changeStringToBoolean(canMoveLeftTop);

//        PrintMng.getInstace().pl(name);
//        PrintMng.getInstace().pl(this.nodeType.name());
//        PrintMng.getInstace().pl(canMoveTop);
//        PrintMng.getInstace().pl( canMoveRightTop);
//        PrintMng.getInstace().pl(canMoveRight);
//        PrintMng.getInstace().pl( canMoveRightBottom);
//        PrintMng.getInstace().pl(canMoveBottom);
//        PrintMng.getInstace().pl( canMoveLeftBottom);
//        PrintMng.getInstace().pl(canMoveLeft);
//        PrintMng.getInstace().pl(canMoveLeftTop);

    }

    public enum NodeType {
        Normal,
        Yet,
        Kill,
        NodeType_End
    }
    private NodeType nodeType = NodeType.NodeType_End;
    private final String name;
    private final boolean canMoveTop;
    private final boolean canMoveRightTop;
    private final boolean canMoveRight;
    private final boolean canMoveRightBottom;
    private final boolean canMoveBottom;
    private final boolean canMoveLeftBottom;
    private final boolean canMoveLeft;
    private final boolean canMoveLeftTop;
    
    private boolean isJack = false;
    private boolean isPolice1 = false;

    public NodeType getNodeType() {
        return nodeType;
    }

    public String getName() {
        return name;
    }

    public boolean isCanMoveTop() {
        return canMoveTop;
    }

    public boolean isCanMoveRightTop() {
        return canMoveRightTop;
    }

    public boolean isCanMoveRight() {
        return canMoveRight;
    }

    public boolean isCanMoveRightBottom() {
        return canMoveRightBottom;
    }

    public boolean isCanMoveBottom() {
        return canMoveBottom;
    }

    public boolean isCanMoveLeftBottom() {
        return canMoveLeftBottom;
    }

    public boolean isCanMoveLeft() {
        return canMoveLeft;
    }

    public boolean isCanMoveLeftTop() {
        return canMoveLeftTop;
    }

    public void inJack() {
    	isJack = true;
    }
    public void inPolice1() {
    	isPolice1 = true;
    }
    public void outJack() {
    	isJack = false;
    }
    public void outPolice1() {
    	isPolice1 = false;
    }
    
    public void setKillNode(){
        if(nodeType != NodeType.Yet){
            System.out.println("살인 일어날 구역 아닌데 살인 터짐요! 미쳤으요?!");
            return;
        }
        nodeType = NodeType.Kill;
    }

    @Override
    public String toString(){
    	
    	String outPut = "";
    	
    	if(isJack) {
    		outPut += "J";
    	}
    	if(isPolice1) {
    		outPut += "P";
    	}
    	
    	if(outPut.isEmpty()) {
    		if(nodeType == NodeType.Normal) {
    			outPut += "○";
    		}
    		else if(nodeType == NodeType.Yet) {
    			outPut += "◐";
    		}
    		else if (nodeType == NodeType.Kill) {
    			outPut += "●";
    		}
    	}
    	
    	return outPut;
    }

    private NodeType changeStringToNodeType(String str){
        return switch (str) {
            case "0" -> NodeType.Normal;
            case "1" -> NodeType.Yet;
            case "2" -> NodeType.Kill;
            default -> NodeType.NodeType_End;
        };
    }

    private boolean changeStringToBoolean(String str){
        return str.equals("1");
    }

	public String setKill() {
		if(nodeType == NodeType.Yet) {
			nodeType = NodeType.Kill;
			return name;
		}
		return "";
	}

	public boolean isCanKill() {
		if(nodeType == NodeType.Yet) {
			return true;
		}
		return false;
	}
}