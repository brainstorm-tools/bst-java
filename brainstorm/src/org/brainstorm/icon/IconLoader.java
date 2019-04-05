package org.brainstorm.icon;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * @author Francois Tadel
 */
public class IconLoader {
    // Window icon
    public static final ImageIcon ICON_APP          = createImageIcon("icon/bstico.png");
    // Tree icons : special
    public static final ImageIcon ICON_OBJECT       = createImageIcon("icon/iconObject.gif");
    public static final ImageIcon ICON_FOLDER_OPEN  = createImageIcon("icon/iconFolderOpen.gif");
    public static final ImageIcon ICON_FOLDER_CLOSE = createImageIcon("icon/iconFolderClose.gif");
    public static final ImageIcon ICON_LOADING      = createImageIcon("icon/iconLoading.gif");
    // Tree icons : files
    public static final ImageIcon ICON_SUBJECTDB    = createImageIcon("icon/iconSubjectDB.gif");
    public static final ImageIcon ICON_SUBJECT      = createImageIcon("icon/iconSubject.gif");
    public static final ImageIcon ICON_ANATOMY      = createImageIcon("icon/iconMri.gif");
    public static final ImageIcon ICON_MRI_AXIAL    = createImageIcon("icon/iconMriAxial.gif");
    public static final ImageIcon ICON_SURFACE      = createImageIcon("icon/iconSurface.gif");
    public static final ImageIcon ICON_SURFACE_CORTEX     = createImageIcon("icon/iconCortexCut.gif");
    public static final ImageIcon ICON_SURFACE_SCALP      = createImageIcon("icon/iconScalp.gif");
    public static final ImageIcon ICON_SURFACE_OUTERSKULL = createImageIcon("icon/iconOuterSkull.gif");
    public static final ImageIcon ICON_SURFACE_INNERSKULL = createImageIcon("icon/iconInnerSkull.gif");
    public static final ImageIcon ICON_STUDYDB_COND  = createImageIcon("icon/iconStudyDBCond.gif");
    public static final ImageIcon ICON_STUDYDB_SUBJ  = createImageIcon("icon/iconStudyDBSubj.gif");
    public static final ImageIcon ICON_STUDY         = createImageIcon("icon/iconStudy.gif");
    public static final ImageIcon ICON_STUDYSUBJECT  = createImageIcon("icon/iconSubject.gif");
    public static final ImageIcon ICON_DATA          = createImageIcon("icon/iconEeg.gif");
    public static final ImageIcon ICON_DATA_LIST     = createImageIcon("icon/iconEegList.gif");
    public static final ImageIcon ICON_CHANNEL       = createImageIcon("icon/iconChannel.gif");
    public static final ImageIcon ICON_HEADMODEL     = createImageIcon("icon/iconHeadModel.gif");
    public static final ImageIcon ICON_RESULTS       = createImageIcon("icon/iconResult.gif");
    public static final ImageIcon ICON_RESULTS_LIST  = createImageIcon("icon/iconResultList.gif");
    public static final ImageIcon ICON_RESULTS_LINK  = createImageIcon("icon/iconResultLink.gif");
    public static final ImageIcon ICON_DEFAULT_STUDY = createImageIcon("icon/iconDefaultStudy.gif");
    public static final ImageIcon ICON_IMAGE         = createImageIcon("icon/iconImage.gif");
    public static final ImageIcon ICON_VIDEO         = createImageIcon("icon/iconVideo.png");
    public static final ImageIcon ICON_NOISECOV      = createImageIcon("icon/iconNoiseCov.gif");
    public static final ImageIcon ICON_DIPOLES       = createImageIcon("icon/iconDipoles.gif");
    public static final ImageIcon ICON_TIMEFREQ      = createImageIcon("icon/iconTimefreq.gif");
    public static final ImageIcon ICON_TIMEFREQ_LIST = createImageIcon("icon/iconTimefreqList.gif");
    public static final ImageIcon ICON_HILBERT       = createImageIcon("icon/iconHilbert.gif");
    public static final ImageIcon ICON_FFT           = createImageIcon("icon/iconFft.gif");
    public static final ImageIcon ICON_MATRIX        = createImageIcon("icon/iconMatrix.gif");
    public static final ImageIcon ICON_MATRIX_LIST   = createImageIcon("icon/iconMatrixList.gif");
    public static final ImageIcon ICON_EMPTY         = createImageIcon("icon/iconEmpty.gif");
    public static final ImageIcon ICON_CONNECT1      = createImageIcon("icon/iconConnect1.gif");
    public static final ImageIcon ICON_CONNECTN      = createImageIcon("icon/iconConnectN.gif");
    public static final ImageIcon ICON_PAC           = createImageIcon("icon/iconPac.gif");
    public static final ImageIcon ICON_PDATA         = createImageIcon("icon/iconPData.gif");
    public static final ImageIcon ICON_PRESULTS      = createImageIcon("icon/iconPResult.gif");
    public static final ImageIcon ICON_PTIMEFREQ     = createImageIcon("icon/iconPTimefreq.gif");
    public static final ImageIcon ICON_PSPECTRUM     = createImageIcon("icon/iconPSpectrum.gif");
    public static final ImageIcon ICON_PHILBERT      = createImageIcon("icon/iconPHilbert.gif");
    public static final ImageIcon ICON_PCONNECT1     = createImageIcon("icon/iconPConnect1.gif");
    public static final ImageIcon ICON_PCONNECTN     = createImageIcon("icon/iconPConnectN.gif");
    public static final ImageIcon ICON_PPAC          = createImageIcon("icon/iconPPac.gif");
    public static final ImageIcon ICON_PMATRIX       = createImageIcon("icon/iconPMatrix.gif");
    
    // Colormaps
    public static final ImageIcon ICON_COLORMAP_GREY   = createImageIcon("icon/iconColormapGrey.gif");
    public static final ImageIcon ICON_COLORMAP_BONE   = createImageIcon("icon/iconColormapBone.gif");
    public static final ImageIcon ICON_COLORMAP_PINK   = createImageIcon("icon/iconColormapPink.gif");
    public static final ImageIcon ICON_COLORMAP_COPPER = createImageIcon("icon/iconColormapCopper.gif");
    public static final ImageIcon ICON_COLORMAP_HOT    = createImageIcon("icon/iconColormapHot.gif");
    public static final ImageIcon ICON_COLORMAP_HOT2   = createImageIcon("icon/iconColormapHot2.gif");
    public static final ImageIcon ICON_COLORMAP_COOL   = createImageIcon("icon/iconColormapCool.gif");
    public static final ImageIcon ICON_COLORMAP_HSV    = createImageIcon("icon/iconColormapHsv.gif");
    public static final ImageIcon ICON_COLORMAP_JET    = createImageIcon("icon/iconColormapJet.gif");
    public static final ImageIcon ICON_COLORMAP_JETINV = createImageIcon("icon/iconColormapJetInv.gif");
    public static final ImageIcon ICON_COLORMAP_RBW    = createImageIcon("icon/iconColormapRbw.gif");
    public static final ImageIcon ICON_COLORMAP_PARULA     = createImageIcon("icon/iconColormapParula.gif");
    public static final ImageIcon ICON_COLORMAP_NEUROSPEED = createImageIcon("icon/iconColormapNeurospeed.gif");
    public static final ImageIcon ICON_COLORMAP_RAINRAMP   = createImageIcon("icon/iconColormapRainramp.gif");
    public static final ImageIcon ICON_COLORMAP_NIH        = createImageIcon("icon/iconColormapNih.gif");
    public static final ImageIcon ICON_COLORMAP_NIHFIRE    = createImageIcon("icon/iconColormapNihFire.gif");
    public static final ImageIcon ICON_COLORMAP_SPECTRUM   = createImageIcon("icon/iconColormapSpectrum.gif");
    public static final ImageIcon ICON_COLORMAP_GE         = createImageIcon("icon/iconColormapGe.gif");
    public static final ImageIcon ICON_COLORMAP_ATLAS      = createImageIcon("icon/iconColormapAtlas.gif");
    public static final ImageIcon ICON_COLORMAP_CLUSTER    = createImageIcon("icon/iconColormapCluster.gif");
    public static final ImageIcon ICON_COLORMAP_CUSTOM     = createImageIcon("icon/iconColormapCustom.gif");
    public static final ImageIcon ICON_COLORMAP_RECORDINGS = createImageIcon("icon/iconColormapRecordings.gif");
    public static final ImageIcon ICON_COLORMAP_SOURCES    = createImageIcon("icon/iconColormapSources.gif");
    public static final ImageIcon ICON_COLORMAP_ANATOMY    = createImageIcon("icon/iconColormapAnatomy.gif");
    public static final ImageIcon ICON_COLORMAP_STAT       = createImageIcon("icon/iconColormapStat.gif");
    public static final ImageIcon ICON_COLORMAP_TIME       = createImageIcon("icon/iconColormapTime.gif");
    public static final ImageIcon ICON_COLORMAP_TIMEFREQ   = createImageIcon("icon/iconColormapTimefreq.gif");
    public static final ImageIcon ICON_COLORMAP_CONNECT    = createImageIcon("icon/iconColormapConnect.gif");
    public static final ImageIcon ICON_COLORMAP_PAC        = createImageIcon("icon/iconColormapPac.gif");
    public static final ImageIcon ICON_COLORMAP_TPAC       = createImageIcon("icon/iconColormapTpac.gif");
    public static final ImageIcon ICON_COLORMAP_GIN        = createImageIcon("icon/iconColormapGin.gif");
    public static final ImageIcon ICON_COLORMAP_OVUN       = createImageIcon("icon/iconColormapOvun.gif");
    public static final ImageIcon ICON_COLORMAP_DIV_LIN    = createImageIcon("icon/iconColormapDivLin.gif");
    public static final ImageIcon ICON_COLORMAP_HOT_LIN    = createImageIcon("icon/iconColormapHotLin.gif");
    public static final ImageIcon ICON_COLORMAP_MAGMA      = createImageIcon("icon/iconColormapMagma.gif");
    public static final ImageIcon ICON_COLORMAP_SEMI_ISO   = createImageIcon("icon/iconColormapSemiIso.gif");
    public static final ImageIcon ICON_COLORMAP_VIRIDIS    = createImageIcon("icon/iconColormapViridis.gif");
    public static final ImageIcon ICON_COLORMAP_VIRIDIS2   = createImageIcon("icon/iconColormapViridis2.gif");
    
    // Good/Bad
    public static final ImageIcon ICON_CHANNEL_GOOD  = createImageIcon("icon/iconChannelGood.gif");
    public static final ImageIcon ICON_CHANNEL_BAD   = createImageIcon("icon/iconChannelBad.gif");
    public static final ImageIcon ICON_GOOD          = createImageIcon("icon/iconGood.gif");
    public static final ImageIcon ICON_BAD           = createImageIcon("icon/iconBad.gif");
    public static final ImageIcon ICON_GOODBAD       = createImageIcon("icon/iconGoodBad.gif");
    
    // DataBase navigator
    public static final ImageIcon ICON_NEXT_SUBJECT   = createImageIcon("icon/iconNextSubject.gif");
    public static final ImageIcon ICON_NEXT_CONDITION = createImageIcon("icon/iconNextCondition.gif");
    public static final ImageIcon ICON_NEXT_DATA      = createImageIcon("icon/iconNextData.gif");
    public static final ImageIcon ICON_NEXT_RESULT    = createImageIcon("icon/iconNextResult.gif");
    public static final ImageIcon ICON_PREVIOUS_SUBJECT   = createImageIcon("icon/iconPreviousSubject.gif");
    public static final ImageIcon ICON_PREVIOUS_CONDITION = createImageIcon("icon/iconPreviousCondition.gif");
    public static final ImageIcon ICON_PREVIOUS_DATA      = createImageIcon("icon/iconPreviousData.gif");
    public static final ImageIcon ICON_PREVIOUS_RESULT    = createImageIcon("icon/iconPreviousResult.gif");
    
    // Various
    public static final ImageIcon ICON_ALIGN_CHANNELS= createImageIcon("icon/iconAlignChannels.gif");
    public static final ImageIcon ICON_ALIGN_SURFACES= createImageIcon("icon/iconAlignSurface.gif");
    public static final ImageIcon ICON_ARROW         = createImageIcon("icon/iconArrow.gif");
    public static final ImageIcon ICON_ATLAS         = createImageIcon("icon/iconAtlas.gif");
    public static final ImageIcon ICON_AXES          = createImageIcon("icon/iconAxes.gif");
    public static final ImageIcon ICON_CHANNEL_LABEL = createImageIcon("icon/iconChannelLabel.gif");
    public static final ImageIcon ICON_ECOG          = createImageIcon("icon/iconEcog.gif");
    public static final ImageIcon ICON_SEEG          = createImageIcon("icon/iconSeeg.gif");
    public static final ImageIcon ICON_NEW_SEL       = createImageIcon("icon/iconNewSel.gif");
    public static final ImageIcon ICON_NEW_IND       = createImageIcon("icon/iconNewInd.gif");
    public static final ImageIcon ICON_CHECK_ALIGN   = createImageIcon("icon/iconCheckAlign.gif");
    public static final ImageIcon ICON_CONDITION     = createImageIcon("icon/iconCondition.gif");
    public static final ImageIcon ICON_CONTACTSHEET  = createImageIcon("icon/iconContactSheet.gif");
    public static final ImageIcon ICON_CORTEX        = createImageIcon("icon/iconCortex.gif");
    public static final ImageIcon ICON_RESULT        = createImageIcon("icon/iconResult.gif");
    public static final ImageIcon ICON_RESULT_KERNEL = createImageIcon("icon/iconResultKernel.gif");
    public static final ImageIcon ICON_DELETE        = createImageIcon("icon/iconDelete.gif");
    public static final ImageIcon ICON_DISPLAY       = createImageIcon("icon/iconDisplay.gif");
    public static final ImageIcon ICON_DOCK          = createImageIcon("icon/iconDock.gif");
    public static final ImageIcon ICON_DOWNSAMPLE    = createImageIcon("icon/iconDownsample.gif");
    public static final ImageIcon ICON_EDIT          = createImageIcon("icon/iconEdit.gif");
    public static final ImageIcon ICON_EXPLORER      = createImageIcon("icon/iconExplorer.gif");
    public static final ImageIcon ICON_TERMINAL      = createImageIcon("icon/iconTerminal.gif");
    public static final ImageIcon ICON_FILTER        = createImageIcon("icon/iconFilter.gif");
    public static final ImageIcon ICON_FLIP          = createImageIcon("icon/iconFlip.gif");
    public static final ImageIcon ICON_FUSION        = createImageIcon("icon/iconFusion.gif");
    public static final ImageIcon ICON_GRID_X        = createImageIcon("icon/iconGridX.gif");
    public static final ImageIcon ICON_GRID_Y        = createImageIcon("icon/iconGridY.gif");
    public static final ImageIcon ICON_HISTOGRAM     = createImageIcon("icon/iconHistogram.gif");
    public static final ImageIcon ICON_LABELS        = createImageIcon("icon/iconLabels.gif");
    public static final ImageIcon ICON_LAYOUT_SHOWALL= createImageIcon("icon/iconLayoutShowAll.gif");
    public static final ImageIcon ICON_LAYOUT_TILE   = createImageIcon("icon/iconLayoutTile.gif");
    public static final ImageIcon ICON_LAYOUT_SELECT = createImageIcon("icon/iconLayoutSelect.gif");
    public static final ImageIcon ICON_LAYOUT_WEIGHT = createImageIcon("icon/iconLayoutWeight.gif");
    public static final ImageIcon ICON_LAYOUT_FULLAREA   = createImageIcon("icon/iconLayoutFullarea.gif");
    public static final ImageIcon ICON_LAYOUT_FULLSCREEN = createImageIcon("icon/iconLayoutFullscreen.gif");
    public static final ImageIcon ICON_LAYOUT_CASCADE    = createImageIcon("icon/iconLayoutCascade.gif");
    public static final ImageIcon ICON_LAYOUT_NONE   = createImageIcon("icon/iconLayoutNone.gif");
    public static final ImageIcon ICON_SCREEN1       = createImageIcon("icon/iconScreen1.gif");
    public static final ImageIcon ICON_SCREEN2       = createImageIcon("icon/iconScreen2.gif");
    public static final ImageIcon ICON_LEGEND        = createImageIcon("icon/iconLegend.gif");
    public static final ImageIcon ICON_MATLAB        = createImageIcon("icon/iconMatlab.gif");
    public static final ImageIcon ICON_MATLAB_IMPORT = createImageIcon("icon/iconMatlabImport.gif");
    public static final ImageIcon ICON_MATLAB_EXPORT = createImageIcon("icon/iconMatlabExport.gif");
    public static final ImageIcon ICON_MATLAB_CONTROLS= createImageIcon("icon/iconMatlabControls.gif");
    public static final ImageIcon ICON_MOVE          = createImageIcon("icon/iconMove.gif");
    public static final ImageIcon ICON_MOVE_CHANNEL  = createImageIcon("icon/iconMoveChannel.gif");
    public static final ImageIcon ICON_MOVIE         = createImageIcon("icon/iconMovie.gif");
    public static final ImageIcon ICON_PLOTEDIT      = createImageIcon("icon/iconPlotEdit.gif");
    public static final ImageIcon ICON_RECYCLE       = createImageIcon("icon/iconRecycle.gif");
    public static final ImageIcon ICON_RELOAD        = createImageIcon("icon/iconReload.gif");
    public static final ImageIcon ICON_RESET         = createImageIcon("icon/iconReset.gif");
    public static final ImageIcon ICON_RESIZE        = createImageIcon("icon/iconResize.gif");
    public static final ImageIcon ICON_ROTATE        = createImageIcon("icon/iconRotate.gif");
    public static final ImageIcon ICON_RUN           = createImageIcon("icon/iconRun.gif");
    public static final ImageIcon ICON_SAVE          = createImageIcon("icon/iconSave.gif");
    public static final ImageIcon ICON_NEW           = createImageIcon("icon/iconFile.gif");
    public static final ImageIcon ICON_SCALE         = createImageIcon("icon/iconScale.gif");
    public static final ImageIcon ICON_SCOUT_FORWARDMODEL = createImageIcon("icon/iconScoutForwardModel.gif");
    public static final ImageIcon ICON_SCOUT_NEW     = createImageIcon("icon/iconScoutNew.gif");
    public static final ImageIcon ICON_SCOUT_SEL     = createImageIcon("icon/iconScoutSel.gif");
    public static final ImageIcon ICON_SCOUT_ALL     = createImageIcon("icon/iconScoutAll.gif");
    public static final ImageIcon ICON_SCOUT_TR100   = createImageIcon("icon/iconScoutTransp100.gif");
    public static final ImageIcon ICON_SCOUT_TR70    = createImageIcon("icon/iconScoutTransp70.gif");
    public static final ImageIcon ICON_SCOUT_TR0     = createImageIcon("icon/iconScoutTransp0.gif");
    public static final ImageIcon ICON_SCOUT_TEXT    = createImageIcon("icon/iconScoutText.gif");
    public static final ImageIcon ICON_SCOUT_CONTOUR = createImageIcon("icon/iconScoutContour.gif");
    public static final ImageIcon ICON_SLICES        = createImageIcon("icon/iconSlices.gif");
    public static final ImageIcon ICON_SNAPSHOT      = createImageIcon("icon/iconSnapshot.gif");
    public static final ImageIcon ICON_SPECTRUM      = createImageIcon("icon/iconSpectrum.gif");
    public static final ImageIcon ICON_STAT_AA       = createImageIcon("icon/iconStatAA.gif");
    public static final ImageIcon ICON_STAT_AB       = createImageIcon("icon/iconStatAB.gif"); 
    public static final ImageIcon ICON_SURFACE_ADD   = createImageIcon("icon/iconSurfaceAdd.gif");
    public static final ImageIcon ICON_SURFACE_REMOVE= createImageIcon("icon/iconSurfaceRemove.gif");
    public static final ImageIcon ICON_THRESHOLD     = createImageIcon("icon/iconThreshold.gif");
    public static final ImageIcon ICON_TOPOGRAPHY    = createImageIcon("icon/iconTopography.gif");
    public static final ImageIcon ICON_TOPO_NOINTERP = createImageIcon("icon/iconTopoNoInterp.gif");
    public static final ImageIcon ICON_2DLAYOUT      = createImageIcon("icon/icon2DLayout.gif");
    public static final ImageIcon ICON_TS_DISPLAY    = createImageIcon("icon/iconTSDisplay.gif");
    public static final ImageIcon ICON_TS_EXPORT     = createImageIcon("icon/iconTSExport.gif");
    public static final ImageIcon ICON_TS_NEW        = createImageIcon("icon/iconTSNew.gif");
    public static final ImageIcon ICON_TS_DISPLAY_MODE = createImageIcon("icon/iconTSDisplayMode.gif");
    public static final ImageIcon ICON_MONTAGE_MENU   = createImageIcon("icon/iconMontageMenu.gif");
    public static final ImageIcon ICON_WARNING       = createImageIcon("icon/iconWarning.gif");
    public static final ImageIcon ICON_WRAP_SURFACE  = createImageIcon("icon/iconWarpSurface.gif");
    public static final ImageIcon ICON_ZOOM          = createImageIcon("icon/iconZoom.gif");
    public static final ImageIcon ICON_ZOOM_PLUS     = createImageIcon("icon/iconZoomPlus.gif");
    public static final ImageIcon ICON_ZOOM_MINUS    = createImageIcon("icon/iconZoomMinus.gif");
    public static final ImageIcon ICON_ZOOM_UP       = createImageIcon("icon/iconZoomUp.gif");
    public static final ImageIcon ICON_ZOOM_DOWN     = createImageIcon("icon/iconZoomDown.gif");
    public static final ImageIcon ICON_ZOOM_X        = createImageIcon("icon/iconZoomX.gif");
    public static final ImageIcon ICON_ZOOM_Y        = createImageIcon("icon/iconZoomY.gif");
    public static final ImageIcon ICON_ZOOM_XY       = createImageIcon("icon/iconZoomXY.gif");
    public static final ImageIcon ICON_SCROLL_UP     = createImageIcon("icon/iconScrollUp.gif");
    public static final ImageIcon ICON_SCROLL_DOWN   = createImageIcon("icon/iconScrollDown.gif");
    public static final ImageIcon ICON_TS_SELECTION  = createImageIcon("icon/iconTSSelection.gif");
    public static final ImageIcon ICON_TS_SYNCRO     = createImageIcon("icon/iconTSSynchro.gif");
    public static final ImageIcon ICON_FLIPY         = createImageIcon("icon/iconFlipY.gif");
    public static final ImageIcon ICON_ALLCOMP       = createImageIcon("icon/iconAllComp.gif");
    public static final ImageIcon ICON_KEYBOARD      = createImageIcon("icon/iconKeyboard.gif");
    
    // New
    public static final ImageIcon ICON_EEG_NEW     = createImageIcon("icon/iconEegNew.gif");
    public static final ImageIcon ICON_EEG_NEW_MENU= createImageIcon("icon/iconEegNewMenu.gif");
    public static final ImageIcon ICON_FOLDER_NEW  = createImageIcon("icon/iconFolderNew.gif");
    public static final ImageIcon ICON_SUBJECT_NEW = createImageIcon("icon/iconSubjectNew.gif");
    // Modifiers
    public static final ImageIcon ICON_MODIF_BAD   = createImageIcon("icon/iconModifBad.gif");
    // FILES
    public static final ImageIcon ICON_COPY        = createImageIcon("icon/iconCopy.gif");
    public static final ImageIcon ICON_CUT         = createImageIcon("icon/iconCut.gif");
    public static final ImageIcon ICON_PASTE       = createImageIcon("icon/iconPaste.gif");

    // MATLAB BUTTON BITMAPS
    public static final ImageIcon ICON_CROSS       = createImageIcon("icon/iconCross.gif");
    public static final ImageIcon ICON_MRI_FLIP    = createImageIcon("icon/iconMriFlip.gif");
    public static final ImageIcon ICON_MRI_PERMUTE = createImageIcon("icon/iconMriPermute.gif");
    public static final ImageIcon ICON_MRI_ROTATE  = createImageIcon("icon/iconMriRotate.gif");
    public static final ImageIcon ICON_FID_LPA     = createImageIcon("icon/iconFiducialLpa.gif");
    public static final ImageIcon ICON_FID_LPA_OK  = createImageIcon("icon/iconFiducialLpaOk.gif");    
    public static final ImageIcon ICON_FID_RPA     = createImageIcon("icon/iconFiducialRpa.gif");
    public static final ImageIcon ICON_FID_RPA_OK  = createImageIcon("icon/iconFiducialRpaOk.gif");
    public static final ImageIcon ICON_FID_NAS     = createImageIcon("icon/iconFiducialNas.gif");
    public static final ImageIcon ICON_FID_NAS_OK  = createImageIcon("icon/iconFiducialNasOk.gif");
    public static final ImageIcon ICON_OK          = createImageIcon("icon/iconOk.gif");
    public static final ImageIcon ICON_TRANSLATION_X = createImageIcon("icon/iconTranslationX.gif");
    public static final ImageIcon ICON_TRANSLATION_Y = createImageIcon("icon/iconTranslationY.gif");
    public static final ImageIcon ICON_TRANSLATION_Z = createImageIcon("icon/iconTranslationZ.gif");
    public static final ImageIcon ICON_ROTATION_X    = createImageIcon("icon/iconRotationX.gif");
    public static final ImageIcon ICON_ROTATION_Y    = createImageIcon("icon/iconRotationY.gif");
    public static final ImageIcon ICON_ROTATION_Z    = createImageIcon("icon/iconRotationZ.gif");
    public static final ImageIcon ICON_RESIZE_X      = createImageIcon("icon/iconResizeX.gif");
    public static final ImageIcon ICON_RESIZE_Y      = createImageIcon("icon/iconResizeY.gif");
    public static final ImageIcon ICON_RESIZE_Z      = createImageIcon("icon/iconResizeZ.gif");
    public static final ImageIcon ICON_PROJECT_ELECTRODES = createImageIcon("icon/iconProjectElectrodes.gif");
    public static final ImageIcon ICON_PAN           = createImageIcon("icon/iconPan.gif");
    public static final ImageIcon ICON_BRIGHTNESS_PLUS  = createImageIcon("icon/iconBrightnessPlus.gif");
    public static final ImageIcon ICON_BRIGHTNESS_MINUS = createImageIcon("icon/iconBrightnessMinus.gif");  
    public static final ImageIcon ICON_LASSO            = createImageIcon("icon/iconLasso.gif");
    public static final ImageIcon ICON_LASSO_MINUS      = createImageIcon("icon/iconLassoMinus.gif");
    public static final ImageIcon ICON_LASSO_PLUS       = createImageIcon("icon/iconLassoPlus.gif");
    public static final ImageIcon ICON_CURVE            = createImageIcon("icon/iconCurve.gif");
    public static final ImageIcon ICON_CURVE_MINUS      = createImageIcon("icon/iconCurveMinus.gif");
    public static final ImageIcon ICON_CURVE_PLUS       = createImageIcon("icon/iconCurvePlus.gif");
    public static final ImageIcon ICON_VIEW_SCOUT_IN_MRI = createImageIcon("icon/iconViewScoutInMri.gif");
    public static final ImageIcon ICON_EDIT_SCOUT_IN_MRI = createImageIcon("icon/iconEditScoutInMri.gif");
    public static final ImageIcon ICON_MENU              = createImageIcon("icon/iconMenu.gif");
    public static final ImageIcon ICON_MENU_LEFT         = createImageIcon("icon/iconMenuLeft.gif");
    public static final ImageIcon ICON_MENU_RIGHT        = createImageIcon("icon/iconMenuRight.gif");
    public static final ImageIcon ICON_MENU_UP           = createImageIcon("icon/iconMenuUp.gif");
    public static final ImageIcon ICON_FIND_MAX          = createImageIcon("icon/iconFindMax.gif");
    public static final ImageIcon ICON_COLOR_SELECTION   = createImageIcon("icon/iconColorSelection.gif");
    public static final ImageIcon ICON_PROPERTIES        = createImageIcon("icon/iconProperties.gif");
    public static final ImageIcon ICON_AVG_REF           = createImageIcon("icon/iconAvgRef.gif");
    public static final ImageIcon ICON_LOBE              = createImageIcon("icon/iconLobe.gif");
    public static final ImageIcon ICON_LOG               = createImageIcon("icon/iconLog.gif");
    public static final ImageIcon ICON_PLUS              = createImageIcon("icon/iconPlus.gif");
    public static final ImageIcon ICON_MINUS             = createImageIcon("icon/iconMinus.gif");
    public static final ImageIcon ICON_SEEG_SPHERE       = createImageIcon("icon/iconSeegSphere.gif");
    public static final ImageIcon ICON_SEEG_DEPTH        = createImageIcon("icon/iconSeegDepth.gif");
    public static final ImageIcon ICON_PLOTLY            = createImageIcon("icon/iconPlotly.gif");
    public static final ImageIcon ICON_SPIKE_SORTING     = createImageIcon("icon/iconSpikeSorting.gif");
    
    // MARKERS
    public static final ImageIcon ICON_EVT_TYPE         = createImageIcon("icon/iconEvtType.gif");
    public static final ImageIcon ICON_EVT_TYPE_ADD     = createImageIcon("icon/iconEvtTypeAdd.gif");
    public static final ImageIcon ICON_EVT_TYPE_DEL     = createImageIcon("icon/iconEvtTypeDel.gif");
    public static final ImageIcon ICON_EVT_OCCUR        = createImageIcon("icon/iconEvtOccur.gif");
    public static final ImageIcon ICON_EVT_OCCUR_ADD    = createImageIcon("icon/iconEvtOccurAdd.gif");
    public static final ImageIcon ICON_EVT_OCCUR_DEL    = createImageIcon("icon/iconEvtOccurDel.gif");
    // RAW
    public static final ImageIcon ICON_RAW_DATA         = createImageIcon("icon/iconRawData.gif");
    public static final ImageIcon ICON_RAW_FOLDER_OPEN  = createImageIcon("icon/iconRawFolderOpen.gif");
    public static final ImageIcon ICON_RAW_FOLDER_CLOSE = createImageIcon("icon/iconRawFolderClose.gif");

    // PROCESSES
    public static final ImageIcon ICON_PROCESS          = createImageIcon("icon/iconProcess.gif");
    public static final ImageIcon ICON_PROCESS_SELECT   = createImageIcon("icon/iconProcessSelect.gif");
    public static final ImageIcon ICON_PROCESS_ADD      = createImageIcon("icon/iconProcessAdd.gif");
    public static final ImageIcon ICON_ARROW_UP         = createImageIcon("icon/iconArrowUp.gif");
    public static final ImageIcon ICON_ARROW_DOWN       = createImageIcon("icon/iconArrowDown.gif");
    public static final ImageIcon ICON_ARROW_LEFT       = createImageIcon("icon/iconArrowLeft.gif");
    public static final ImageIcon ICON_ARROW_RIGHT      = createImageIcon("icon/iconArrowRight.gif");
    public static final ImageIcon ICON_OPEN_LIST        = createImageIcon("icon/iconOpenList.gif");
    public static final ImageIcon ICON_SAVE_LIST        = createImageIcon("icon/iconSaveList.gif");
    public static final ImageIcon ICON_DELETE_LIST      = createImageIcon("icon/iconDeleteList.gif");
    public static final ImageIcon ICON_PIPELINE_LIST    = createImageIcon("icon/iconPipelineList.gif");

    // ===== CONSTRUCTOR =====
    public IconLoader() {
    }
    
    
    // ===== CREATE ICON =====
    // Returns an ImageIcon, or null if the path was invalid.
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = org.brainstorm.TestClass.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    
    // ===== GET ICON AS AN ARRAY OR PIXELS =====
    public static int [] getIcon_RGB(String iconName){
        try{
            // Get icon
            ImageIcon icon = (ImageIcon) IconLoader.class.getField(iconName).get(null);
            if (icon == null){
                return null;
            }
            // Create buffered image
            BufferedImage bfIcon = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            bfIcon.createGraphics().drawImage(icon.getImage(), 0, 0, icon.getImageObserver());
            // Get image informations
            int H = bfIcon.getHeight();
            int W = bfIcon.getWidth();
            int nbComp = bfIcon.getColorModel().getNumComponents();
            int nbValues = W * H * nbComp;
            int [] argb = new int[nbValues + 3];
            // Get RGB array
            bfIcon.getData().getPixels(0, 0, W, H, argb);
            // Add descriptor at the end of the array: [W,H,nbComp]
            argb[nbValues + 0] = W;
            argb[nbValues + 1] = H;
            argb[nbValues + 2] = nbComp;
            // Return RGB array
            return argb;
            
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
    }
    
    // ===== SET SCALING FACTOR =====
    public static ImageIcon scaleIcon(ImageIcon imgIcon, float scaling){
        if (scaling != 1){
            java.awt.Image img = imgIcon.getImage();
            img = img.getScaledInstance(Math.round(imgIcon.getIconWidth() * scaling), Math.round(imgIcon.getIconHeight() * scaling),  java.awt.Image.SCALE_SMOOTH);
            imgIcon = new ImageIcon(img);
        }
        return imgIcon;
    }
}











