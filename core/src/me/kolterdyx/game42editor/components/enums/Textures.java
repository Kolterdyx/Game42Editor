package me.kolterdyx.game42editor.components.enums;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import me.kolterdyx.game42editor.components.utils.Counter;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.jetbrains.annotations.ApiStatus.*;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import java.io.*;
import java.net.URI;

public enum Textures {
    RED_TILE("textures/tiles/red_tile.svg"),
    GREEN_TILE("textures/tiles/green_tile.svg"),
    BLUE_TILE("textures/tiles/blue_tile.svg"),
    BLANK_TILE("textures/tiles/blank_tile.svg"),
    BOX("textures/widgets/box.svg"),
    DARK_BOX("textures/widgets/box_darkened.svg"),
    TARGET("textures/tiles/target.svg"),
    RED_TARGET("textures/tiles/red_target.svg"),
    GREEN_TARGET("textures/tiles/green_target.svg"),
    BLUE_TARGET("textures/tiles/blue_target.svg"),
    RED_PAINT("textures/sprites/red_paint.svg"),
    GREEN_PAINT("textures/sprites/green_paint.svg"),
    BLUE_PAINT("textures/sprites/blue_paint.svg"),
    ARROW_UP("textures/sprites/arrow_up.svg"),
    ARROW_LEFT("textures/sprites/arrow_left.svg"),
    ARROW_RIGHT("textures/sprites/arrow_right.svg"),
    ROCKET("textures/sprites/rocket.svg"),
    SELECTOR_STARTPOINT("textures/widgets/selector_startpoint.svg"),
    DARK_SELECTOR_STARTPOINT("textures/widgets/selector_startpoint_darkened.svg"),
    SELECTOR_ENDPOINT("textures/widgets/selector_endpoint.svg"),
    DARK_SELECTOR_ENDPOINT("textures/widgets/selector_endpoint_darkened.svg"),
    SELECTOR_MIDPOINT("textures/widgets/selector_midpoint.svg"),
    DARK_SELECTOR_MIDPOINT("textures/widgets/selector_midpoint_darkened.svg"),
    SELECTOR_STANDALONE("textures/widgets/selector_standalone.svg"),
    DARK_SELECTOR_STANDALONE("textures/widgets/selector_standalone_darkened.svg");

    private String filename;
    private int id;

    Textures(String filename){
        this.filename = filename;
        this.id = Counter.getTextureId();
    }


    public Texture getWithSize(float width, float height){
        return renderTexture(width, height);
    }

    public Texture getWithSize(float size){
        return renderTexture(size, size);
    }

    private Texture renderTexture(float width, float height){

        URI inputUri = new File(this.filename).toURI();

        try {
            SVGDocument doc = new SAXSVGDocumentFactory((XMLResourceDescriptor.getXMLParserClassName())).createSVGDocument(inputUri.toString());

            Element svg = doc.getDocumentElement();

            if (width != height) {
                NodeList rects = svg.getElementsByTagName("rect");
                float strokeWidth = 0;
                for (int i = 0; i < rects.getLength(); i++) {
                    NamedNodeMap rect = rects.item(i).getAttributes();
                    rect.getNamedItem("width").setNodeValue(String.valueOf(width));
                    rect.getNamedItem("height").setNodeValue(String.valueOf(height));
                    String[] styleList = rect.getNamedItem("style").getNodeValue().split(";");
                    String styleValue = "stroke-width:0";
                    for (String s : styleList) {
                        if (s.startsWith("stroke-width")) {
                            styleValue = s;
                            break;
                        }
                    }
                    strokeWidth = Float.parseFloat(styleValue.split(":")[1]);
                }

                if (strokeWidth > 0) {
                    svg.setAttribute("viewBox", "0 0 " + (int) (width + strokeWidth) + " " + (int) (height + strokeWidth));
                } else {
                    svg.setAttribute("viewBox", "0 0 " + width + " " + height);
                }
            }

            ByteArrayOutputStream resultByteStream = new ByteArrayOutputStream();

            TranscoderInput input = new TranscoderInput(doc);
            TranscoderOutput output = new TranscoderOutput(resultByteStream);
//            TranscoderOutput output2 = new TranscoderOutput(new FileOutputStream(this.filename+".png"));
            PNGTranscoder t = new PNGTranscoder();
            t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
            t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);
            t.transcode(input, output);
//            t.transcode(input, output2);

            resultByteStream.flush();
            Gdx2DPixmap gpm = new Gdx2DPixmap(new ByteArrayInputStream(resultByteStream.toByteArray()), Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
            Pixmap pixmap = new Pixmap(gpm);
            Texture texture = new Texture(pixmap);
            pixmap.dispose();
            resultByteStream.close();
            return texture;

        } catch (IOException | TranscoderException e) {
            e.printStackTrace();
        }
        return null;

    }

    public int getId(){
        return id;
    }

    @Experimental
    public static Texture combineTextures(Texture background, Texture foreground) {
//        background.getTextureData().prepare();
        Pixmap pixmap1 = background.getTextureData().consumePixmap();
//        foreground.getTextureData().prepare();
        Pixmap pixmap2 = foreground.getTextureData().consumePixmap();

        Pixmap pixmap3 = new Pixmap(background.getWidth(), background.getHeight(), pixmap1.getFormat());

        pixmap3.drawPixmap(pixmap2, 0, 0);
        pixmap3.drawPixmap(pixmap1, 0, 0);
        Texture textureResult = new Texture(pixmap3);

        pixmap3.dispose();

        return textureResult;
    }
}
