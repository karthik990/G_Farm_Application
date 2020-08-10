package com.nightonke.boommenu;

import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.BoomButtons.BoomButtonBuilder;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import java.util.ArrayList;

class ExceptionManager {
    ExceptionManager() {
    }

    static void judge(BoomMenuButton boomMenuButton, ArrayList<BoomButtonBuilder> arrayList) {
        int i;
        int i2;
        if (boomMenuButton.getButtonEnum() == null || boomMenuButton.getButtonEnum().equals(ButtonEnum.Unknown)) {
            throw new RuntimeException("Unknown button-enum!");
        } else if (boomMenuButton.getPiecePlaceEnum() == null || boomMenuButton.getPiecePlaceEnum().equals(PiecePlaceEnum.Unknown)) {
            throw new RuntimeException("Unknown piece-place-enum!");
        } else if (boomMenuButton.getButtonPlaceEnum() == null || boomMenuButton.getButtonPlaceEnum().equals(ButtonPlaceEnum.Unknown)) {
            throw new RuntimeException("Unknown button-place-enum!");
        } else if (boomMenuButton.getBoomEnum() == null || boomMenuButton.getBoomEnum().equals(BoomEnum.Unknown)) {
            throw new RuntimeException("Unknown boom-enum!");
        } else if (arrayList == null || arrayList.size() == 0) {
            throw new RuntimeException("Empty builders!");
        } else {
            int pieceNumber = boomMenuButton.getPiecePlaceEnum().pieceNumber();
            int minPieceNumber = boomMenuButton.getPiecePlaceEnum().minPieceNumber();
            int maxPieceNumber = boomMenuButton.getPiecePlaceEnum().maxPieceNumber();
            int size = boomMenuButton.getCustomPiecePlacePositions().size();
            int buttonNumber = boomMenuButton.getButtonPlaceEnum().buttonNumber();
            int minButtonNumber = boomMenuButton.getButtonPlaceEnum().minButtonNumber();
            int maxButtonNumber = boomMenuButton.getButtonPlaceEnum().maxButtonNumber();
            int size2 = boomMenuButton.getCustomButtonPlacePositions().size();
            int size3 = arrayList.size();
            String str = ") is not equal to buttons'(";
            String str2 = ") is not equal to builders'(";
            String str3 = ", ";
            String str4 = "The number of piece(";
            String str5 = ")";
            if (pieceNumber == -1) {
                String str6 = "]) of the piece-place-enum(";
                i = size2;
                String str7 = ") is not in the range([";
                if (buttonNumber != -1 && (minPieceNumber > buttonNumber || buttonNumber > maxPieceNumber)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("The number(");
                    sb.append(buttonNumber);
                    sb.append(") of buttons of button-place-enum(");
                    sb.append(boomMenuButton.getButtonPlaceEnum());
                    sb.append(str7);
                    sb.append(minPieceNumber);
                    sb.append(str3);
                    sb.append(maxPieceNumber);
                    sb.append(str6);
                    sb.append(boomMenuButton.getPiecePlaceEnum());
                    sb.append(str5);
                    throw new RuntimeException(sb.toString());
                } else if (minPieceNumber > size3 || size3 > maxPieceNumber) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("The number of builders(");
                    sb2.append(size3);
                    sb2.append(str7);
                    sb2.append(minPieceNumber);
                    sb2.append(str3);
                    sb2.append(maxPieceNumber);
                    sb2.append(str6);
                    sb2.append(boomMenuButton.getPiecePlaceEnum());
                    sb2.append(str5);
                    throw new RuntimeException(sb2.toString());
                }
            } else {
                i = size2;
                if (buttonNumber != -1) {
                    if (pieceNumber != buttonNumber) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str4);
                        sb3.append(pieceNumber);
                        sb3.append(str);
                        sb3.append(buttonNumber);
                        sb3.append(str5);
                        throw new RuntimeException(sb3.toString());
                    } else if (pieceNumber != size3) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str4);
                        sb4.append(pieceNumber);
                        sb4.append(str2);
                        sb4.append(size3);
                        sb4.append(str5);
                        throw new RuntimeException(sb4.toString());
                    }
                }
            }
            String str8 = "])";
            if (boomMenuButton.getPiecePlaceEnum().equals(PiecePlaceEnum.Custom)) {
                if (size > 0) {
                    if (buttonNumber == -1) {
                        if (minButtonNumber > size || size > maxButtonNumber) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("When the positions of pieces is customized, the length(");
                            sb5.append(size);
                            sb5.append(") of custom-piece-place-positions array is not in the range([");
                            sb5.append(minButtonNumber);
                            sb5.append(str3);
                            sb5.append(maxButtonNumber);
                            sb5.append(str8);
                            throw new RuntimeException(sb5.toString());
                        }
                    } else if (size != buttonNumber) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(str4);
                        sb6.append(size);
                        sb6.append(str);
                        sb6.append(buttonNumber);
                        sb6.append(str5);
                        throw new RuntimeException(sb6.toString());
                    }
                    if (size != size3) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(str4);
                        sb7.append(size);
                        sb7.append(str2);
                        sb7.append(size3);
                        sb7.append(str5);
                        throw new RuntimeException(sb7.toString());
                    }
                } else {
                    throw new RuntimeException("When the positions of pieces are customized, the custom-piece-place-positions array is empty");
                }
            }
            if (!boomMenuButton.getButtonPlaceEnum().equals(ButtonPlaceEnum.Custom)) {
                return;
            }
            if (i > 0) {
                String str9 = "The number of button(";
                if (pieceNumber == -1) {
                    i2 = i;
                    if (minPieceNumber > i2 || i2 > maxPieceNumber) {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("When the positions of buttons is customized, the length(");
                        sb8.append(i2);
                        sb8.append(") of custom-button-place-positions array is not in the range([");
                        sb8.append(minPieceNumber);
                        sb8.append(str3);
                        sb8.append(maxPieceNumber);
                        sb8.append(str8);
                        throw new RuntimeException(sb8.toString());
                    }
                } else {
                    i2 = i;
                    if (i2 != pieceNumber) {
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append(str9);
                        sb9.append(i2);
                        sb9.append(") is not equal to pieces'(");
                        sb9.append(pieceNumber);
                        sb9.append(str5);
                        throw new RuntimeException(sb9.toString());
                    }
                }
                if (i2 != size3) {
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(str9);
                    sb10.append(i2);
                    sb10.append(str2);
                    sb10.append(size3);
                    sb10.append(str5);
                    throw new RuntimeException(sb10.toString());
                }
                return;
            }
            throw new RuntimeException("When the positions of buttons are customized, the custom-button-place-positions array is empty");
        }
    }
}
